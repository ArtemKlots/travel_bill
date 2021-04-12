package com.travelBill.telegram.driver.keyboard;


import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.LoginUrl;
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
                if (((com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton) button).loginUrl != null) {
                    mappedButton.setLoginUrl(mapLoginUrl((com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton) button));
                }
                row.add(mappedButton);
            });
            keyboard.add(row);
        });

        externalMarkup.setKeyboard(keyboard);
        return externalMarkup;
    }

    private LoginUrl mapLoginUrl(com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton button) {
        LoginUrl loginUrl = new LoginUrl();
        loginUrl.setUrl(button.loginUrl.url);
        loginUrl.setBotUsername(button.loginUrl.botUsername);
        loginUrl.setForwardText(button.loginUrl.forwardText);
        loginUrl.setRequestWriteAccess(button.loginUrl.requestWriteAccess);
        return loginUrl;
    }

    public ReplyKeyboard mapTo(com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard markup) {
        org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup externalMarkup = new org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup();
        externalMarkup.setResizeKeyboard(markup.autoResize);
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
