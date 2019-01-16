package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.Context;
import org.telegram.telegrambots.meta.api.objects.Update;

public class EventContext extends Context {
    public EventService eventService;
    public Update update;
    public User currentUser;

}
