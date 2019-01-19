package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class CreateEventScenario extends AbstractEventScenario {
    private static final String SUCCESS_MESSAGE = "Event %s has been successfully registered. " +
            "I have no access to members list, so I need to ask you to join manually. Please press /join ";
    private static final String FAIL_MESSAGE = "Sorry, something went wrong. I can do nothing in this chat";

    public CreateEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage createMessage() {
        Event event;
        String textMessage;
        try {
            event = createEvent(eventContext);
            textMessage = String.format(SUCCESS_MESSAGE, event.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
            textMessage = FAIL_MESSAGE;
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
                .getChat()
                .getTitle();
    }

}
