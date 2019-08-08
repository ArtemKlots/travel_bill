package com.travelBill.telegram.scenario.group.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;

public class DeleteBillResponse extends Response {
    private String responseTemplate = "*%s %s %s* has been successfully removed by *%s*";
    public Bill bill;
    public String actorFullName;

    @Override
    public String getMessage() {
        return String.format(responseTemplate, bill.getAmount(), bill.getCurrency(), bill.getPurpose(), actorFullName);
    }
}
