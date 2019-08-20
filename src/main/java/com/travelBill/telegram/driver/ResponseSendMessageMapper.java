package com.travelBill.telegram.driver;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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

        if (!isNull(response.keyboard)) {
            message.setReplyMarkup(response.keyboard);
        }

        return message;

    }
}
