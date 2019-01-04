package com.travelBill.telegram.scenario;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
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

    public ScenarioFactory(EventService eventService) {
        this.eventService = eventService;
    }

    public Scenario createScenario(Update update, User currentUser) {
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(update);
        Scenario scenario = null;

        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            scenario = new InitialScenario(update);
        }

        if (eventScenarioHelper.isEventAction()) {
            EventContext eventContext = getEventContext(update, currentUser);
            scenario = new EventScenarioFactory().createScenario(eventContext);
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
}
