package com.travelBill.telegram.scenario.individual.event.close;

import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class CloseEventRequestCancelScenario implements Scenario {
    private final BotApi botApi;

    public CloseEventRequestCancelScenario(BotApi botApi) {
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        botApi.deleteMessage(request.chatId, request.messageId);

        Response response = new Response();
        response.message = String.format("Event *%s* was *not* closed", request.user.getCurrentEvent().getTitle());
        response.parseMode = ParseMode.MARKDOWN;

        return response;
    }
}
