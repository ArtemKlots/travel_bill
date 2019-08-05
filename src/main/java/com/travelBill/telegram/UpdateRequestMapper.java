package com.travelBill.telegram;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

public class UpdateRequestMapper {
    public Request mapTo(Update update) {
        Request request = new Request();
        request.chatId = getChat(update).getId();
        request.message = getMessage(update);
        request.messageId = getMessageId(update);
        request.callbackQueryData = getCallbackQueryData(update);
        request.chatType = getChatType(update);
        request.chatTitle = getChatTitle(update);
        request.isGroupChatCreated = isGroupChatCreated(update);

        return request;
    }

    public Chat getChat(Update update) {
        Chat chat;
        if (update.hasMessage()) {
            chat = update.getMessage().getChat();
        } else {
            chat = update.getCallbackQuery().getMessage().getChat();
        }

        return chat;
    }

    public ChatType getChatType(Update update) {
        Chat chat = getChat(update);
        ChatType chatType;

        if (chat.isUserChat()) {
            chatType = ChatType.PRIVATE;
        } else if (chat.isGroupChat()) {
            chatType = ChatType.GROUP;
        } else if (chat.isChannelChat()) {
            chatType = ChatType.CHANEL;
        } else if (chat.isSuperGroupChat()) {
            chatType = ChatType.SUPER_GROUP;
        } else throw new IllegalArgumentException("Unknown chat type");

        return chatType;
    }

    public String getChatTitle(Update update) {
        return this.getChat(update).getTitle();
    }

    public String getCallbackQueryData(Update update) {
        String callbackQueryData = null;

        if (update.hasCallbackQuery()) {
            callbackQueryData = update.getCallbackQuery().getData();
        }

        return callbackQueryData;
    }

    public String getMessage(Update update) {
        String message;
        if (update.hasMessage()) {
            if (update.getMessage().hasText()) {
                message = update.getMessage().getText();
            } else {
                message = null;
            }
            ;
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage().getText();
        } else {
            message = null;
        }
        ;
        return message;
    }

    public Integer getMessageId(Update update) {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getMessageId();
        } else if (update.hasMessage()) {
            return update.getMessage().getMessageId();
        } else throw new IllegalArgumentException();
    }

    public boolean isGroupChatCreated(Update update) {
        return update.hasMessage()
                && (!isNull(update.getMessage().getGroupchatCreated()) || !isNull(update.getMessage().getSuperGroupCreated()));
    }

}
