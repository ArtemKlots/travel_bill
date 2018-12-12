package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.Event;
import com.travelBill.api.core.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ShowCurrentEventScenario {
    static SendMessage perform(EventContext eventContext) {
        User currentUser = eventContext.currentUser;
        Event event = currentUser.getCurrentEvent();

        String responseMessage = String.format("Your current event is \"%s\"", event.getTitle());

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
