package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;

public class AddBillFailResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Cannot add bill. Something went wrong";
        return response;
    }
}
