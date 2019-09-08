package com.travelBill.telegram.driver;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.driver.keyboard.KeyboardMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static java.util.Objects.isNull;

public class ResponseSendMessageMapper {
    public SendMessage mapTo(Response response, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (!isNull(response.message)) {
            message.setText(response.message);
        }

        if (!isNull(response.parseMode)) {
            message.setParseMode(response.parseMode);
        }

        if (!isNull(response.inlineKeyboard)) {
            ReplyKeyboard markup = new KeyboardMapper().mapTo(response.inlineKeyboard);
            message.setReplyMarkup(markup);
        }

        if (!isNull(response.replyKeyboard)) {
            ReplyKeyboard markup = new KeyboardMapper().mapTo(response.replyKeyboard);
            message.setReplyMarkup(markup);
        }

        return message;

    }
}
