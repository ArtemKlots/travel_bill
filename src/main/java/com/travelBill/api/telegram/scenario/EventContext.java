package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.User;
import com.travelBill.api.event.EventService;
import org.telegram.telegrambots.meta.api.objects.Update;

class EventContext {
    EventService eventService;
    Update update;
    User currentUser;

    EventContext() {
    }

    Long getChatId() {
        if (update.getMessage() != null) {
            return update.getMessage().getChatId();
        } else {
            return update.getCallbackQuery().getMessage().getChatId();
        }
    }
}
