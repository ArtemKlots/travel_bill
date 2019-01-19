package com.travelBill.telegram.scenario.common.context;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;

public class BillContext extends Context {
    public EventService eventService;
    public BillService billService;
    public User currentUser;

    public BillContext() {
    }

}
