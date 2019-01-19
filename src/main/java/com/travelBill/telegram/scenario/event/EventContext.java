package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.Context;

public class EventContext extends Context {
    public EventService eventService;
    public User currentUser;

}
