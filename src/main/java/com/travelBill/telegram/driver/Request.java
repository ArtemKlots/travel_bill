package com.travelBill.telegram.driver;

import com.travelBill.api.core.user.User;

public class Request {
    public Long chatId;
    public String message;
    public String callbackQueryData;
    public Integer messageId;
    public User user;

    public ChatType chatType;
    public String chatTitle;
    public boolean isGroupChatCreated;

    public boolean hasMessage() {
        return message != null;
    }

    public boolean hasCallbackQueryData() {
        return callbackQueryData != null;
    }

    @Override
    public String toString() {
        return "Request{" +
                "chatId=" + chatId +
                ", message='" + message + '\'' +
                ", callbackQueryData='" + callbackQueryData + '\'' +
                ", messageId=" + messageId +
                ", user=" + user +
                ", chatType=" + chatType +
                ", chatTitle='" + chatTitle + '\'' +
                ", isGroupChatCreated=" + isGroupChatCreated +
                '}';
    }
}
