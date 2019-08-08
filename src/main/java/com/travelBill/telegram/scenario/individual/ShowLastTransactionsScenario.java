package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;

import java.util.List;


public class ShowLastTransactionsScenario extends AbstractBillScenario {

    ShowLastTransactionsScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public Response execute() {
        List<Bill> bills = billContext.billService.selectTop10ByUserIdOrderByCreatedAtDesc(billContext.currentUser.getId());
        ShowLastTransactionResponse response = new ShowLastTransactionResponse();
        response.bills = bills;
        return response;
    }
}
