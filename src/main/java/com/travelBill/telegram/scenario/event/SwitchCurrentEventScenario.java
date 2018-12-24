package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import javax.persistence.EntityNotFoundException;

public class SwitchCurrentEventScenario extends AbstractEventScenario {
    SwitchCurrentEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public SendMessage createMessage() {
        User currentUser = eventContext.currentUser;
        String callbackQueryCommand = eventContext.update.getCallbackQuery().getData();
        String stringEventId = callbackQueryCommand.split("switch_to_event")[1];
        Event event;

        try {
            Long eventId = Long.parseLong(stringEventId);
            event = eventContext.eventService.findById(eventId);
            eventContext.eventService.switchCurrentEvent(currentUser, event);
        } catch (NumberFormatException | EntityNotFoundException e) {
            return new SendMessage()
                    .setChatId(eventContext.getChatId())
                    .setText("Sorry, but I cant understand your instruction :(");
        }

        String responseMessage = String.format("You have selected \"%s\" as the current event", event.getTitle());

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
