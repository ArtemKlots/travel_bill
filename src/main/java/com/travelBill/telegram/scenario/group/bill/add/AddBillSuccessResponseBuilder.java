package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;

import java.text.DecimalFormat;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class AddBillSuccessResponseBuilder implements ResponseBuilder {
    Bill bill;
    User user;

    @Override
    public Response build() {
        Response response = new Response();
        String amount = new DecimalFormat("#.##").format(bill.getAmount());
        response.message = String.format("Done ;) *%s %s* %s were accepted from *%s*", amount, bill.getCurrency(), bill.getPurpose(), user.getFullName());
        response.parseMode = MARKDOWN;
        return response;
    }
}
