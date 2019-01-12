package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventContext {
    public EventService eventService;
    public Update update;
    public User currentUser;

    public EventContext() {
    }

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
