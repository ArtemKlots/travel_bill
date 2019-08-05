package com.travelBill.telegram;

public class Request {
    public Long chatId;
    public String message;
    public String callbackQueryData;
    public Integer messageId;

    public ChatType chatType;
    public String chatTitle;
    public boolean isGroupChatCreated;

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasCallbackQueryData() {
        return callbackQueryData != null;
    }
}
