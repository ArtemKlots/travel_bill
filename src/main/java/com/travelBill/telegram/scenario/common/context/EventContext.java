package com.travelBill.telegram.scenario.common.context;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;

public class EventContext extends Context {
    public EventService eventService;
    public User currentUser;

}
