package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class CreateEventScenario implements Scenario {
    private final EventService eventService;

    public CreateEventScenario(EventService eventService) {
        this.eventService = eventService;
    }


    @Override
    public Response execute(Request request) {
        ResponseBuilder responseBuilder;

        try {
            Event event = eventService.save(request.chatTitle, request.user, request.chatId);
            responseBuilder = new CreateEventSuccessResponseBuilder();
            ((CreateEventSuccessResponseBuilder) responseBuilder).eventTitle = event.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            responseBuilder = new CreateEventFailResponseBuilder();
        }

        return responseBuilder.build();
    }

}
