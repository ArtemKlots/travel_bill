package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;
import static java.util.Objects.isNull;

@Service
public class ShowCurrentEventScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        Event currentEvent = request.user.getCurrentEvent();
        Response response = new Response();

        response.parseMode = MARKDOWN;

        if (isNull(currentEvent)) {
            response.message = "You don't have a current event yet";
        } else {
            response.message = String.format("*%s* is your current event", currentEvent.getTitle());
        }

        return response;
    }

}
