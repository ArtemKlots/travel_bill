package com.travelBill.telegram.scenario.individual.bill.add;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

import java.text.DecimalFormat;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;

public class AddBillSuccessResponseBuilder implements ResponseBuilder {
    Bill bill;
    User user;

    @Override
    public Response build() {
        Response response = new Response();
        String amount = new DecimalFormat("#.##").format(bill.getAmount());
        response.message = String.format("Done ;) *%s %s* %s were accepted", amount, bill.getCurrency(), bill.getPurpose());
        response.parseMode = MARKDOWN;
        return response;
    }
}
