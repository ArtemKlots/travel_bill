package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

public class CreateEventFailResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Sorry, something went wrong. I can do nothing in this chat";
        return response;
    }
}
