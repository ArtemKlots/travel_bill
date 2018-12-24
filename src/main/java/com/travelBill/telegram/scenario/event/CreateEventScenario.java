package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CreateEventScenario extends AbstractEventScenario {
    CreateEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage createMessage() {
        Event event;
        String textMessage;

        if (!getEventName(eventContext).equals("")) {
            event = createEvent(eventContext);
            textMessage = String.format("Event \"%s\" has been created. Now it is your current event", event.getTitle());
        } else {
            textMessage = "Sorry, but I cant create event without title";
        }

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(textMessage);
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
