package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class JoinEventScenario extends AbstractEventScenario {
    JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public SendMessage createMessage() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());

        eventContext.eventService.addMember(event, eventContext.currentUser);

        String responseMessage = String.format(
                "Hello %s %s! Now I know that you are here and can receive your contributions :)",
                eventContext.currentUser.getFirstName(),
                eventContext.currentUser.getLastName()
        );

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage);
    }
}
