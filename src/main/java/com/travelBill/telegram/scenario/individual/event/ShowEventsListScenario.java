package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class ShowEventsListScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        ShowEventsListResponseBuilder responseBuilder = new ShowEventsListResponseBuilder();
        responseBuilder.events = request.user.getEvents();

        return responseBuilder.build();
    }


}
