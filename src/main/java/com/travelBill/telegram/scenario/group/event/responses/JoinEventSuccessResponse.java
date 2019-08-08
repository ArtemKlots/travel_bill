package com.travelBill.telegram.scenario.group.event.responses;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class JoinEventSuccessResponse extends JoinEventResponse {

    @Override
    public String getMessage() {
        return String.format(
                "Hello %s! Now I know that you are here and can receive your contributions :) \n\n" +
                        "Use the following pattern to make a contribution (spaces are important!): \n<how much> <currency> <purpose> \n\n" +
                        "Example: 10 points to Gryffindor",
                member.getFullName());
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow showDebtsRow = new KeyboardRow();
        showDebtsRow.add("Show debts");

        KeyboardRow deleteBillRow = new KeyboardRow();
        deleteBillRow.add("Delete bill");

        keyboard.add(showDebtsRow);
        keyboard.add(deleteBillRow);

        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        return markup;
    }
}
