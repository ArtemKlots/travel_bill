package com.travelBill.telegram.scenario;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.bill.BillContext;
import com.travelBill.telegram.scenario.bill.BillScenarioFactory;
import com.travelBill.telegram.scenario.bill.BillScenarioResolver;
import com.travelBill.telegram.scenario.common.Scenario;
import com.travelBill.telegram.scenario.event.EventContext;
import com.travelBill.telegram.scenario.event.EventScenarioFactory;
import com.travelBill.telegram.scenario.event.EventScenarioHelper;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

@Service
public class ScenarioFactory {
    private final EventService eventService;
    private final BillService billService;

    public ScenarioFactory(EventService eventService, BillService billService) {
        this.eventService = eventService;
        this.billService = billService;
    }

    public Scenario createScenario(Update update, User currentUser) {
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(update);
        BillScenarioResolver billScenarioResolver = new BillScenarioResolver(update);
        Scenario scenario = null;

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            scenario = new InitialScenario(update);
        }

        if (eventScenarioHelper.isEventAction()) {
            EventContext eventContext = getEventContext(update, currentUser);
            scenario = new EventScenarioFactory().createScenario(eventContext);
        }

        if (billScenarioResolver.isBillAction()) {
            BillContext billContext = getBillContext(update, currentUser);
            scenario = new BillScenarioFactory().createScenario(billContext);
        }

        if (isNull(scenario)) {
            scenario = new UnknownScenario(update);
        }

        return scenario;
    }


    private EventContext getEventContext(Update update, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }

    // TODO: 16.01.19 refactor
    private BillContext getBillContext(Update update, User currentUser) {
        BillContext context = new BillContext();
        context.eventService = eventService;
        context.billService = billService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }
}
