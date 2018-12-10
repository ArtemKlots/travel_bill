package com.travelBill.api.telegram.scenario;

import com.travelBill.api.telegram.TelegramEventService;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventContext {
    TelegramEventService telegramEventService;
    Update update;
    Long chatId;

    public EventContext() {
    }

    EventContext(TelegramEventService telegramEventService, Update update) {
        this.telegramEventService = telegramEventService;
        this.update = update;
        this.chatId = update.getMessage().getChatId();
    }
}
