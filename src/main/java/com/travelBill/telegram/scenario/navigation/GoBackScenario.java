package com.travelBill.telegram.scenario.navigation;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.PrivateChatKeyboardBuilder;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public class GoBackScenario implements Scenario {
    private final PrivateChatKeyboardBuilder privateChatKeyboardBuilder = new PrivateChatKeyboardBuilder();

    @Override
    public Response execute(Request request) {
        Response response = new Response("Done");
        response.replyKeyboard = privateChatKeyboardBuilder.build();
        return response;
    }

}
