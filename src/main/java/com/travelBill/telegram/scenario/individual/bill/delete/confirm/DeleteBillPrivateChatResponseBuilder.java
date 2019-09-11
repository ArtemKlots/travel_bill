package com.travelBill.telegram.scenario.individual.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

import static com.travelBill.ParseMode.MARKDOWN;

public class DeleteBillPrivateChatResponseBuilder implements ResponseBuilder {
    private static final String responseTemplate = "*%s %s %s* has been successfully removed";
    public Bill bill;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = String.format(responseTemplate, bill.getAmount(), bill.getCurrency(), bill.getPurpose());
        response.parseMode = MARKDOWN;
        return response;
    }
}
