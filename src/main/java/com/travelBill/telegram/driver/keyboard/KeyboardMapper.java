package com.travelBill.telegram.driver.keyboard;


import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class KeyboardMapper {

    public ReplyKeyboard mapTo(InlineKeyboard markup) {
        org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup externalMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

        markup.rows.forEach(buttons -> {
            List<InlineKeyboardButton> row = new ArrayList<>();
            buttons.forEach(button -> {
                com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton typedButton = (com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton) button;
                InlineKeyboardButton mappedButton = new InlineKeyboardButton();
                mappedButton.setText(typedButton.text);
                mappedButton.setUrl(typedButton.url);
                mappedButton.setCallbackData(typedButton.callbackData);
                row.add(mappedButton);
            });
            keyboard.add(row);
        });

        externalMarkup.setKeyboard(keyboard);
        return externalMarkup;
    }

    public ReplyKeyboard mapTo(com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard markup) {
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup externalMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        markup.rows.forEach(buttons -> {
            KeyboardRow row = new KeyboardRow();
            buttons.forEach(button -> {
                ReplyKeyboardButton typedButton = (ReplyKeyboardButton) button;
                KeyboardButton mappedButton = new KeyboardButton();
                mappedButton.setText(typedButton.text);
                row.add(mappedButton);
            });
            keyboard.add(row);
        });

        externalMarkup.setKeyboard(keyboard);
        return externalMarkup;
    }
}
