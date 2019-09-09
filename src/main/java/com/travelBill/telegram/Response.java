package com.travelBill.telegram;

import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;

import static java.util.Objects.isNull;

public class Response {
    public String parseMode;
    public String message;
    public InlineKeyboard inlineKeyboard;
    public ReplyKeyboard replyKeyboard;

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }

    public boolean isEmpty() {
        return isNull(message) && isNull(replyKeyboard) && isNull(inlineKeyboard);
    }
}
