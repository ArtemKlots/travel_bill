package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static com.travelBill.ParseMode.MARKDOWN;
import static java.util.Objects.isNull;

@Service
public class ShowCurrentEventScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        Event currentEvent = request.user.getCurrentEvent();
        Response response = new Response();
        //TODO get rid from external dependency
        response.parseMode = MARKDOWN;

        if (isNull(currentEvent)) {
            response.message = "You don't have a current event yet";
        } else {
            response.message = String.format("*%s* is your current event", currentEvent.getTitle());
        }

        return response;
    }

}
