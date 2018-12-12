package com.travelBill.api.telegram.scenario.event;

import com.travelBill.api.core.User;
import com.travelBill.api.event.EventService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventContext {
    public EventService eventService;
    public Update update;
    public User currentUser;

    public EventContext() {
    }

    public Long getChatId() {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }
}
