package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.event.exceptions.MemberAlreadyInEventException;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class JoinEventScenario implements Scenario {
    private final EventService eventService;

    public JoinEventScenario(EventService eventService) {
        this.eventService = eventService;
    }

    public Response execute(Request request) {
        Event event = eventService.findByTelegramChatId(request.chatId);
        JoinEventResponseBuilder responseBuilder;

        try {
            eventService.addMember(event, request.user);
            responseBuilder = new JoinEventSuccessResponseBuilder();
        } catch (MemberAlreadyInEventException e) {
            responseBuilder = new JoinEventFailResponseBuilder();
        }

        responseBuilder.member = request.user;

        return responseBuilder.build();
    }
}
