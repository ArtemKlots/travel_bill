package com.travelBill.telegram.scenario.common.context;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ContextProvider {
    private final EventService eventService;
    private final BillService billService;

    public ContextProvider(EventService eventService, BillService billService) {
        this.eventService = eventService;
        this.billService = billService;
    }

    public EventContext getEventContext(Update update, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }

    public BillContext getBillContext(Update update, User currentUser) {
        BillContext context = new BillContext();
        context.eventService = eventService;
        context.billService = billService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }
}
