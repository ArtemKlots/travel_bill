package com.travelBill.telegram.scenario;

import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownScenario implements Scenario {
    private Update update;

    public UnknownScenario(Update update) {
        this.update = update;
    }

    @Override
    public SendMessage createMessage() {
        long chat_id = update.getMessage().getChatId();

        return new SendMessage()
                .setChatId(chat_id)
                .setText("Sorry, but I can't understand you :( Could you rephrase please?");
    }
}
