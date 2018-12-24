package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static java.util.Objects.isNull;

public class ShowCurrentEventScenario extends AbstractEventScenario {
    ShowCurrentEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage createMessage() {
        User currentUser = eventContext.currentUser;
        Event event = currentUser.getCurrentEvent();
        String responseMessage;

        if (isNull(event)) {
            responseMessage = "Oh no, it seems you have no events. \n Use \"/create Some event\" to create a new one";
        } else {
            responseMessage = String.format("Your current event is \"%s\"", event.getTitle());
        }

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
