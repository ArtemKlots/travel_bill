package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public class UnknownScenario implements Scenario {
    private Request request;

    public UnknownScenario(Request request) {
        this.request = request;
    }

    @Override
    public Response execute() {


//        return new SendMessage()
//                .setChatId(request.chatId)
//                .setText("Sorry, but I can't understand you :( Could you rephrase please?");
        return new Response("Sorry, but I can't understand you :( Could you rephrase please?");
    }
}
