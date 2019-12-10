package com.travelBill.telegram.scenario.individual.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.ClosedEventResponse;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class DeleteBillScenario implements Scenario {
    private final BillService billService;
    private final BotApi botApi;


    private static final int BILL_ID_INDEX_IN_CALLBACK = 2;

    public DeleteBillScenario(BillService billService, BotApi botApi) {
        this.billService = billService;
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        Event currentEvent = request.user.getCurrentEvent();

        if (isNull(currentEvent)) {
            return new EventIsNotSelectedResponse();
        }

        String stringBillId = request.callbackQueryData.split("_")[BILL_ID_INDEX_IN_CALLBACK];
        Long billId = Long.parseLong(stringBillId);
        Bill billToRemove = billService.findById(billId, request.user.getId());

        try {
            billService.delete(billToRemove, request.user);
            Response groupResponse = buildGroupResponse(request, billToRemove);
            botApi.sendMessage(currentEvent.getTelegramChatId(), groupResponse);
        } catch (ClosedEventException e) {
            return new ClosedEventResponse(currentEvent);
        } finally {
            botApi.deleteMessage(request.chatId, request.messageId);
        }

        DeleteBillPrivateChatResponseBuilder responseBuilder = new DeleteBillPrivateChatResponseBuilder();
        responseBuilder.bill = billToRemove;
        return responseBuilder.build();
    }

    private Response buildGroupResponse(Request request, Bill billToRemove) {
        DeleteBillGroupChatResponseBuilder builder = new DeleteBillGroupChatResponseBuilder();
        builder.bill = billToRemove;
        builder.memberFullName = request.user.getFullName();
        return builder.build();
    }

}
