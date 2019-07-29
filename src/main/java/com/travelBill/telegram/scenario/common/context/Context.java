package com.travelBill.telegram.scenario.common.context;

import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.TelegramUpdateUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.security.InvalidParameterException;

public abstract class Context {
    public Update update;
    public BotApi botApi;

    public Long getChatId() {
        return new TelegramUpdateUtils().getChatId(update);
    }

    public Boolean isGroupOrSuperGroupChat() {
        return update.getMessage().getChat().isGroupChat() || update.getMessage().getChat().isSuperGroupChat();
    }

    public Integer getMessageId() {
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getMessageId();
        } else if (update.hasMessage()) {
            return update.getMessage().getMessageId();
        } else throw new InvalidParameterException();
    }
}
