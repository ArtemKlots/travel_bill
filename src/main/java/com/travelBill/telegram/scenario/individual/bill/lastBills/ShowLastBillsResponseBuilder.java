package com.travelBill.telegram.scenario.individual.bill.lastBills;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.formatter.bill.BillListFormatter;
import com.travelBill.telegram.formatter.bill.LastBillsListFormatter;

import java.util.List;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class ShowLastBillsResponseBuilder implements ResponseBuilder {
    private BillListFormatter formatter = new LastBillsListFormatter();
    List<Bill> bills;

    @Override
    public Response build() {
        Response response = new Response();
        if (bills.size() > 0) {
            response.message = formatter.format(bills);
        } else {
            response.parseMode = MARKDOWN;
            response.message = "It looks like you haven't added any bill yet. Try to add a new one using the following pattern:" +
                    "\n<*how much*> <*currency*> <*purpose*> \n" +
                    "Example: 10 points to Gryffindor\n";
        }

        return response;
    }
}
