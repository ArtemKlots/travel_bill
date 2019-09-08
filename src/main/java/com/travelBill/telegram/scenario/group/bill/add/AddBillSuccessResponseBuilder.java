package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;

public class AddBillSuccessResponseBuilder implements ResponseBuilder {
    String transaction;
    User user;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = String.format("Done ;) %s were accepted from %s", transaction, user.getFullName());
        return response;
    }
}
