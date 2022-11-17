package com.travelBill.telegram.scenario.individual.bill.add;

import com.travelBill.RollbarLogger;
import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.api.core.user.User;
import com.travelBill.sns.BillCreatedEvent;
import com.travelBill.sns.EventManagerService;
import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;
import com.travelBill.telegram.scenario.ClosedEventResponse;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.bill.AddBillCommandParser;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;
import static java.util.Objects.isNull;

@Service
public class AddBillScenario implements Scenario {
    private final BillService billService;
    private final EventService eventService;
    private final BotApi botApi;
    private final EventManagerService eventManagerService;
    private final RollbarLogger rollbarLogger;

    public AddBillScenario(BillService billService, EventService eventService, BotApi botApi, EventManagerService eventManagerService, RollbarLogger rollbarLogger) {
        this.billService = billService;
        this.eventService = eventService;
        this.botApi = botApi;
        this.eventManagerService = eventManagerService;
        this.rollbarLogger = rollbarLogger;
    }

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        User user = request.user;
        Event event = eventService.findById(request.user.getCurrentEvent().getId());
        String textMessage = request.message;
        ResponseBuilder responseBuilder;
        Bill bill;

        try {
            bill = createBillFromTextMessage(user, event, textMessage);

            responseBuilder = new AddBillSuccessResponseBuilder();
            ((AddBillSuccessResponseBuilder) responseBuilder).bill = bill;
            ((AddBillSuccessResponseBuilder) responseBuilder).user = user;

            notifyInEventChat(user, event, bill);
        } catch (ClosedEventException e) {
            return new ClosedEventResponse(event);
        } catch (Exception e) {
            responseBuilder = new AddBillFailResponseBuilder();
            e.printStackTrace();
            return responseBuilder.build();
        }

        try {
            BillCreatedEvent billCreatedEvent =
                    new BillCreatedEvent(request.chatId, request.messageId, bill.getId(), event.getId(), user.getId(), bill.getPurpose());
            this.eventManagerService.sendBillCreatedEvent("bill.created", billCreatedEvent);
        } catch (Exception e) {
            e.printStackTrace();
            rollbarLogger.error(e, "Error on assigning tag: ".concat(request.toString()));
        }

        return responseBuilder.build();
    }

    private Bill createBillFromTextMessage(User user, Event event, String textMessage) {
        Bill bill = AddBillCommandParser.parse(textMessage);
        bill.setEvent(event);
        bill.setUser(user);
        billService.save(bill);
        return bill;
    }

    private void notifyInEventChat(User user, Event event, Bill bill) {
        Response eventChatResponse = new Response(String.format(
                "*%s %s* %s were accepted from *%s*",
                bill.getAmount(), bill.getCurrency(), bill.getPurpose(), user.getFullName()));
        eventChatResponse.parseMode = MARKDOWN;

        botApi.sendMessage(event.getTelegramChatId(), eventChatResponse);
    }
}
