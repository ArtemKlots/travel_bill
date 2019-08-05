package com.travelBill.telegram.scenario.common.context;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.BotApi;
import com.travelBill.telegram.Request;
import org.springframework.stereotype.Service;

@Service
public class ContextProvider {
    private final EventService eventService;
    private final BillService billService;
    private final BotApi botApi;

    public ContextProvider(EventService eventService, BillService billService, BotApi botApi) {
        this.eventService = eventService;
        this.billService = billService;
        this.botApi = botApi;
    }

    public EventContext getEventContext(Request request, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.botApi = botApi;
        context.request = request;
        context.currentUser = currentUser;
        return context;
    }

    public BillContext getBillContext(Request request, User currentUser) {
        BillContext context = new BillContext();
        context.eventService = eventService;
        context.billService = billService;
        context.botApi = botApi;
        context.request = request;
        context.currentUser = currentUser;
        return context;
    }
}
