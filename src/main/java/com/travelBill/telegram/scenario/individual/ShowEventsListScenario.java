package com.travelBill.telegram.scenario.individual;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;

public class ShowEventsListScenario extends AbstractEventScenario {
    ShowEventsListScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public Response execute() {
        Long userId = eventContext.currentUser.getId();

        ShowEventsListResponseBuilder responseBuilder = new ShowEventsListResponseBuilder();
        responseBuilder.events = eventContext.eventService.getEventsByOwnerId(userId);

        return responseBuilder.build();
    }


}
