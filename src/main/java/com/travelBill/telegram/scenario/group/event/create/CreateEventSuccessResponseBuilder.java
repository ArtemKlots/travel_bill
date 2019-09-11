package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

public class CreateEventSuccessResponseBuilder implements ResponseBuilder {
    String eventTitle;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = String.format("Event %s has been successfully registered. " +
                "I have no access to members list, so I need to ask you to join manually. Please press /join ", eventTitle);
        return response;
    }

}
