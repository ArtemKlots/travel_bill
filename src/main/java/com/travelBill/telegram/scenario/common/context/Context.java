package com.travelBill.telegram.scenario.common.context;

import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Context {
    public Update update;

    public Long getChatId() {
        return new TelegramUpdateUtils().getChatId(update);
    }

    public Boolean isGroupOrSuperGroupChat() {
        return update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat();
    }
}
