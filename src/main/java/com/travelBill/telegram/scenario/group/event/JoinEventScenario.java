package com.travelBill.telegram.scenario.group.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.exceptions.MemberAlreadyInEventException;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;
import com.travelBill.telegram.scenario.group.event.responses.JoinEventFailResponse;
import com.travelBill.telegram.scenario.group.event.responses.JoinEventResponse;
import com.travelBill.telegram.scenario.group.event.responses.JoinEventSuccessResponse;

public class JoinEventScenario extends AbstractEventScenario {
    public JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public Response execute() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());
        JoinEventResponse response;

        try {
            eventContext.eventService.addMember(event, eventContext.currentUser);
            response = new JoinEventSuccessResponse();
        } catch (MemberAlreadyInEventException e) {
            response = new JoinEventFailResponse();
        }

        response.member = eventContext.currentUser;

        return response;
    }
}
