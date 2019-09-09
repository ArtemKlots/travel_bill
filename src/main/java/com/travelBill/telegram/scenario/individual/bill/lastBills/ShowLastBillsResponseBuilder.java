package com.travelBill.telegram.scenario.individual.bill.lastBills;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.formatter.bill.BillListFormatter;
import com.travelBill.telegram.formatter.bill.LastBillsListFormatter;

import java.util.List;

public class ShowLastBillsResponseBuilder implements ResponseBuilder {
    private BillListFormatter formatter = new LastBillsListFormatter();
    List<Bill> bills;

    @Override
    public Response build() {
        Response response = new Response();
        if (bills.size() > 0) {
            response.message = formatter.format(bills);
        } else {
            response.message = "It looks like you haven't added any bill yet. Maybe you need to change the current event?";
        }

        return response;
    }
}
