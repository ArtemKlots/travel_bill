package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class JoinEventScenario extends AbstractEventScenario {
    JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public SendMessage createMessage() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());

        eventContext.eventService.addMember(event, eventContext.currentUser);

        String responseMessage = String.format(
                "User %s %s has been added to the event",
                eventContext.currentUser.getFirstName(),
                eventContext.currentUser.getLastName()
        );

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
