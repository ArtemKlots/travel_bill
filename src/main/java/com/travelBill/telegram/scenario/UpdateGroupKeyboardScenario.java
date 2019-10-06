package com.travelBill.telegram.scenario;

import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class UpdateGroupKeyboardScenario implements Scenario {
    private final GroupChatKeyboardBuilder groupChatKeyboardBuilder;
    private final ApplicationConfiguration applicationConfiguration;

    public UpdateGroupKeyboardScenario(GroupChatKeyboardBuilder groupChatKeyboardBuilder,
                                       ApplicationConfiguration applicationConfiguration) {
        this.groupChatKeyboardBuilder = groupChatKeyboardBuilder;
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        String botName = applicationConfiguration.getTelegramBotMention();
        response.message = "Hey there! New bot version released! All bot interaction has been moved to the private chat with " + botName;
        response.replyKeyboard = groupChatKeyboardBuilder.build();
        return response;
    }
}
