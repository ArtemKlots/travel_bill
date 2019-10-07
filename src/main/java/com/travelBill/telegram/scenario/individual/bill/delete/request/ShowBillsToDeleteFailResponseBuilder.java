package com.travelBill.telegram.scenario.individual.bill.delete.request;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

public class ShowBillsToDeleteFailResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "You haven't added any bill to this event";
        return response;
    }
}
