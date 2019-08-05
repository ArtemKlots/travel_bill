package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InitialScenario implements Scenario {
    private Request request;

    InitialScenario(Request request) {
        this.request = request;
    }

    @Override
    public SendMessage createMessage() {
        long chatId = request.chatId;
        ReplyKeyboard markup = createMarkup();

        return new SendMessage()
                .setChatId(chatId)
                .setText("What would you like to do?")
                .setReplyMarkup(markup);
    }

    private ReplyKeyboard createMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow currentEventRow = new KeyboardRow();
        currentEventRow.add("Show current event");

        KeyboardRow seeEventsRow = new KeyboardRow();
        seeEventsRow.add("Show events");

        KeyboardRow lastTransactionRow = new KeyboardRow();
        lastTransactionRow.add("Show last transactions");

        keyboard.add(seeEventsRow);
//        keyboard.add(currentEventRow);
        keyboard.add(lastTransactionRow);

        markup.setKeyboard(keyboard);
        return markup;
    }
}
