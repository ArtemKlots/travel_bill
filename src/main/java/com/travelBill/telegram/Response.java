package com.travelBill.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

// todo make interface
public class Response {
    private String message;
    public Long chatId;
    public String parseMode;

    // TODO: Rework it
    private ReplyKeyboard replyKeyboard;

    public String getMessage() {
        return message;
    }

    public ReplyKeyboard getKeyboard() {
        return replyKeyboard;
    }

    public Response() {
    }

    public Response(String message) {
        this.message = message;
    }
}
