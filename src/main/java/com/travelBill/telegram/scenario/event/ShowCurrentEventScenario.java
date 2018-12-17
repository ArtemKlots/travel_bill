package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class ShowCurrentEventScenario extends AbstractEventScenario {
    ShowCurrentEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage perform() {
        User currentUser = eventContext.currentUser;
        Event event = currentUser.getCurrentEvent();

        String responseMessage = String.format("Your current event is \"%s\"", event.getTitle());

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
