package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;
import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

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
