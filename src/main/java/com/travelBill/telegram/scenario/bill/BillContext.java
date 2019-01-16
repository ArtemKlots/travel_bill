package com.travelBill.telegram.scenario.bill;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.Context;

public class BillContext extends Context {
    public EventService eventService;
    public BillService billService;
    public User currentUser;

    public BillContext() {
    }

}
