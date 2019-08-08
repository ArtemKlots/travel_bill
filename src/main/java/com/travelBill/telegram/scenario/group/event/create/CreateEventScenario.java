package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;

public class CreateEventScenario extends AbstractEventScenario {

    public CreateEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public Response execute() {
        Response response;

        try {
            Event event = createEvent(eventContext);
            response = new CreateEventSuccessResponse();
            ((CreateEventSuccessResponse) response).eventTitle = event.getTitle();
        } catch (Exception e) {
            e.printStackTrace();
            response = new CreateEventFailResponse();
        }

        return response;
    }

    private static Event createEvent(EventContext eventContext) {
        String eventName = eventContext.request.chatTitle;
        return eventContext.eventService.save(eventName, eventContext.currentUser, eventContext.getChatId());
    }

}
