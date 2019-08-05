package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class UnknownScenario implements Scenario {
    private Request request;

    public UnknownScenario(Request request) {
        this.request = request;
    }

    @Override
    public SendMessage createMessage() {
        return new SendMessage()
                .setChatId(request.chatId)
                .setText("Sorry, but I can't understand you :( Could you rephrase please?");
    }
}
