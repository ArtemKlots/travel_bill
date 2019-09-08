package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.event.EventService;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class ShowEventsListScenario implements Scenario {
    private final EventService eventService;

    public ShowEventsListScenario(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public Response execute(Request request) {
        Long userId = request.user.getId();

        ShowEventsListResponseBuilder responseBuilder = new ShowEventsListResponseBuilder();
        responseBuilder.events = eventService.getEventsByOwnerId(userId);

        return responseBuilder.build();
    }


}
