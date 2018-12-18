package com.travelBill.telegram.scenario;

import com.travelBill.telegram.scenario.common.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InitialScenario implements Scenario {
    private Update update;

    InitialScenario(Update update) {
        this.update = update;
    }

    @Override
    public SendMessage perform() {
        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText("What would you like to do?");

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow currentEventRow = new KeyboardRow();
        currentEventRow.add("Show current event");

        KeyboardRow seeEventsRow = new KeyboardRow();
        seeEventsRow.add("Show events");

        keyboard.add(seeEventsRow);
        keyboard.add(currentEventRow);

        markup.setKeyboard(keyboard);

        message.setReplyMarkup(markup);

        return message;
    }
}
