package com.travelBill.telegram.scenario.individual.event.close;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static com.travelBill.Icons.LOCKED;

@Service
public class CloseEventRequestSubmitScenario implements Scenario {
    private final BotApi botApi;
    private final EventService eventService;

    public CloseEventRequestSubmitScenario(BotApi botApi, EventService eventService) {
        this.botApi = botApi;
        this.eventService = eventService;
    }

    @Override
    public Response execute(Request request) {
        Event event = request.user.getCurrentEvent();
        botApi.deleteMessage(request.chatId, request.messageId);

        Response response = new Response();
        response.parseMode = ParseMode.MARKDOWN;

        try {
            eventService.closeEvent(event.getId(), request.user.getId());
            response.message = String.format("Event *%s* was successfully *closed* ", event.getTitle());
            botApi.sendMessage(event.getTelegramChatId(), new Response(String.format("%s %s closed this event", LOCKED, request.user.getFullName())));
        } catch (ClosedEventException e) {
            response.message = String.format("Cannot close event %s. *It is already closed*", event.getTitle());
        }

        return response;
    }
}
