package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class UpdateIndividualKeyboardScenario implements Scenario {
    private final PrivateChatKeyboardBuilder privateChatKeyboardBuilder;

    public UpdateIndividualKeyboardScenario(PrivateChatKeyboardBuilder privateChatKeyboardBuilder) {
        this.privateChatKeyboardBuilder = privateChatKeyboardBuilder;
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        response.message = "Hey there! New bot version released! Your keyboard has just been updated!";
        response.replyKeyboard = privateChatKeyboardBuilder.build();
        return response;
    }
}
