package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class DeletePreviousMessageScenario implements Scenario {
    private final BotApi botApi;

    public DeletePreviousMessageScenario(BotApi botApi) {
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        botApi.deleteMessage(request.chatId, request.messageId);
        return new Response();
    }

}
