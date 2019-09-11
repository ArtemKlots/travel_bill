package com.travelBill.telegram.scenario.individual.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

import static com.travelBill.ParseMode.MARKDOWN;

public class DeleteBillGroupChatResponseBuilder implements ResponseBuilder {
    private static final String responseTemplate = "*%s %s %s* has been successfully removed by *%s*";
    public Bill bill;
    public String memberFullName;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = String.format(responseTemplate, bill.getAmount(), bill.getCurrency(), bill.getPurpose(), memberFullName);
        response.parseMode = MARKDOWN;
        return response;
    }
}
