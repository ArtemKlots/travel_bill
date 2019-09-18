package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class UpdateGroupKeyboardScenario implements Scenario {
    private final GroupChatKeyboardBuilder groupChatKeyboardBuilder;

    public UpdateGroupKeyboardScenario(GroupChatKeyboardBuilder groupChatKeyboardBuilder) {
        this.groupChatKeyboardBuilder = groupChatKeyboardBuilder;
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        response.message = "Hey there! New bot version released! All bot interaction has been moved to the private chat.";
        response.replyKeyboard = groupChatKeyboardBuilder.build();
        return response;
    }
}
