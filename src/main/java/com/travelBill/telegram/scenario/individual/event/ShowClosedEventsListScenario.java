package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowClosedEventsListScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        ShowEventsListResponseBuilder responseBuilder = new ShowEventsListResponseBuilder();
        responseBuilder.events = request.user
                .getEvents()
                .stream()
                .filter(event -> !event.isOpened())
                .sorted(Comparator.comparing(Event::getClosedAt).thenComparing(Event::getCreatedAt))
                .collect(Collectors.toList());
        return responseBuilder.build();
    }
}
