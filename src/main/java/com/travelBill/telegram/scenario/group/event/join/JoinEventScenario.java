package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.exceptions.MemberAlreadyInEventException;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;

public class JoinEventScenario extends AbstractEventScenario {
    public JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public Response execute() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());
        JoinEventResponseBuilder responseBuilder;

        try {
            eventContext.eventService.addMember(event, eventContext.currentUser);
            responseBuilder = new JoinEventSuccessResponseBuilder();
        } catch (MemberAlreadyInEventException e) {
            responseBuilder = new JoinEventFailResponseBuilder();
        }

        responseBuilder.member = eventContext.currentUser;

        return responseBuilder.build();
    }
}
