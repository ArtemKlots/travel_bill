package com.travelBill.telegram.scenario.individual.bill.delete.cancel;

import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class CancelBillDeletingScenario implements Scenario {
    private final BotApi botApi;

    public CancelBillDeletingScenario(BotApi botApi) {
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        botApi.deleteMessage(request.chatId, request.messageId);
        return new Response();
    }

}
