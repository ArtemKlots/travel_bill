package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.formatter.bill.BillListFormatter;
import com.travelBill.telegram.formatter.bill.LastTransactionsListFormatter;

import java.util.List;

public class ShowLastTransactionResponseBuilder implements ResponseBuilder {
    private BillListFormatter formatter = new LastTransactionsListFormatter();
    List<Bill> bills;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = formatter.format(bills);
        return response;
    }
}
