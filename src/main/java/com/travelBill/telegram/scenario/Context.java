package com.travelBill.telegram.scenario;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Context {
    public Update update;

    public Long getChatId() {
        if (update.hasMessage()) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }

    public Boolean isGroupOrSuperGroupChat() {
        return update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat();
    }
}
