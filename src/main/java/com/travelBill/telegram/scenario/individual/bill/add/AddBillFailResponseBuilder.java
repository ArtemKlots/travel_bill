package com.travelBill.telegram.scenario.individual.bill.add;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

public class AddBillFailResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Cannot add bill. Something went wrong";
        return response;
    }
}
