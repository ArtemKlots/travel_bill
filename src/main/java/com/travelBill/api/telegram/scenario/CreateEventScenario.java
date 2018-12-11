package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

class CreateEventScenario {

    static SendMessage perform(EventContext eventContext) {
        Event event = createEvent(eventContext);

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(String.format("Event %s has been created. Now it is your current event", event.getTitle()));
    }

    private static Event createEvent(EventContext eventContext) {
        String eventName = getEventName(eventContext);
        return eventContext.eventService.create(eventName, eventContext.currentUser);
    }

    private static String getEventName(EventContext eventContext) {
        return eventContext.update
                .getMessage()
                .getText()
                .replaceFirst("(?i)/create", "")
                .trim();
    }
}
