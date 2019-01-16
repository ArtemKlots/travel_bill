package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static java.util.Objects.isNull;

public class CreateEventScenario extends AbstractEventScenario {
    CreateEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage createMessage() {
        Event event;
        Event existingEvent = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());

        String textMessage;
        if (!eventContext.isGroupOrSuperGroupChat()) {
            textMessage = "Sorry, but this action allowed in group chat only";
        } else if (!isNull(existingEvent)) {
            textMessage = String.format("This chat already linked with an event \"%s\"", existingEvent.getTitle());
        } else if (!getEventName(eventContext).equals("")) {
            event = createEvent(eventContext);
            textMessage = String.format("Event \"%s\" has been created. Use /join to join the event", event.getTitle());
        } else {
            textMessage = "Sorry, but I cant create event without title";
        }

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(textMessage);
    }

    private static Event createEvent(EventContext eventContext) {
        String eventName = getEventName(eventContext);
        return eventContext.eventService.save(eventName, eventContext.currentUser, eventContext.getChatId());
    }

    private static String getEventName(EventContext eventContext) {
        return eventContext.update
                .getMessage()
                .getText()
                .replaceFirst("(?i)/create", "")
                .trim();
    }

}
