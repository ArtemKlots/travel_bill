package com.travelBill.telegram.driver;

import com.travelBill.telegram.driver.keyboard.KeyboardMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import static java.util.Objects.isNull;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.HTML;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class ResponseSendMessageMapper {
    public SendMessage mapTo(Response response, Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);

        if (!isNull(response.message)) {
            message.setText(response.message);
        }

        if (!isNull(response.parseMode)) {
            message.setParseMode(getParseMode(response));
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

    private String getParseMode(Response response) {
        String parseMode;
        switch (response.parseMode) {
            case MARKDOWN:
                parseMode = MARKDOWN;
                break;
            case HTML:
                parseMode = HTML;
                break;
            case PLAIN_TEXT:
            default:
                parseMode = null;
        }
        return parseMode;
    }
}
