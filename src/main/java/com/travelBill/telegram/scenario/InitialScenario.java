package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public class InitialScenario implements Scenario {
    private Request request;

    InitialScenario(Request request) {
        this.request = request;
    }

    @Override
    public Response execute() {

//        return new SendMessage()
//                .setChatId(chatId)
//                .setText("What would you like to do?")
//                .setReplyMarkup(markup);
        return new InitialScenarioResponse();
    }


}
