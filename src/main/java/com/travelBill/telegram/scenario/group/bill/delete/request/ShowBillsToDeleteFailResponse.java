package com.travelBill.telegram.scenario.group.bill.delete.request;

import com.travelBill.telegram.Response;

public class ShowBillsToDeleteFailResponse extends Response {

    @Override
    public String getMessage() {
        return "Нou haven't added any bill to this event";
    }
}
