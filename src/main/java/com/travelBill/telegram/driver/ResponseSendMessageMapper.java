package com.travelBill.telegram.driver;

import com.travelBill.telegram.driver.keyboard.KeyboardMapper;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static java.util.Objects.isNull;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.HTML;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class ResponseSendMessageMapper {
    public SendMessage mapTo(Response response, Long chatId) {
        SendMessage message = new SendMessage();
        // According to latest changes User identifiers will have up to 52 significant bits, so a 64-bit integer or double-precision float type would still be safe for storing them.
        // So, current Long type should be enough
        // https://core.telegram.org/bots/api#march-9-2021
        message.setChatId(chatId.toString());

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
            ReplyKeyboard markup;
            if (response.replyKeyboard.isDeleteKeyboard) {
                markup = new ReplyKeyboardRemove();
            } else {
                markup = new KeyboardMapper().mapTo(response.replyKeyboard);
            }
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
