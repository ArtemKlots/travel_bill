package com.travelBill.telegram.driver;

import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;

import static java.util.Objects.isNull;

public class Response {
    public ParseMode parseMode = ParseMode.PLAIN_TEXT;
    public String message;
    public InlineKeyboard inlineKeyboard;
    public ReplyKeyboard replyKeyboard;

    public Response() {
    }

    public Response(ParseMode parseMode) {
        this.parseMode = parseMode;
    }

    public Response(String message) {
        this.message = message;
    }

    public Response(ParseMode parseMode, String message) {
        this.parseMode = parseMode;
        this.message = message;
    }

    public boolean isEmpty() {
        return isNull(message) && isNull(replyKeyboard) && isNull(inlineKeyboard);
    }
}
