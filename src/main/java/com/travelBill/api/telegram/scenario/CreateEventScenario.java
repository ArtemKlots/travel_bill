package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

class CreateEventScenario {

    static SendMessage perform(EventContext eventContext) {
        String eventName = eventContext.update
                .getMessage()
                .getText()
                .replaceFirst("(?i)/create", "")
                .trim(); // (?i) - case insensitive

        Event event = eventContext.telegramEventService.create(eventName);
        return new SendMessage()
                .setChatId(eventContext.chatId)
                .setText(String.format("Event %s has been created. Now it is your current event", event.getTitle()));
    }
}
