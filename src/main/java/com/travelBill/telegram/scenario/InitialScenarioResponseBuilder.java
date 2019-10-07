package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;

@Service
public class InitialScenarioResponseBuilder implements ResponseBuilder {
    private final PrivateChatKeyboardBuilder privateChatKeyboardBuilder;

    public InitialScenarioResponseBuilder(PrivateChatKeyboardBuilder privateChatKeyboardBuilder) {
        this.privateChatKeyboardBuilder = privateChatKeyboardBuilder;
    }

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Hey there! Let's travel\n" +
                "1. Join the event or create a new one by adding the bot to your group chat\n" +
                "2. Use the following pattern to make a contribution (spaces are important!): \n<*how much*> <*currency*> <*purpose*> \n" +
                "Example: 10 points to Gryffindor\n" +
                "3. Ask me to calculate your debts and I'll do it for you";
        response.replyKeyboard = privateChatKeyboardBuilder.build();
        response.parseMode = MARKDOWN;
        return response;
    }

}
