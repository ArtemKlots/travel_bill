package com.travelBill.telegram.scenario;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.common.Scenario;
import com.travelBill.telegram.scenario.common.ScenarioTypes;
import com.travelBill.telegram.scenario.event.EventContext;
import com.travelBill.telegram.scenario.event.EventScenarioFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScenarioFactory {
    private final EventService eventService;

    public ScenarioFactory(EventService eventService) {
        this.eventService = eventService;
    }

    public Scenario getScenario(Update update, User currentUser) {
        ScenarioTypes type = ScenarioTypes.defineType(update);

        if (type == null) {
            return new UnknownScenario(update);
        }

        return selectScenario(type, update, currentUser);
    }

    private Scenario selectScenario(ScenarioTypes type, Update update, User currentUser) {
        switch (type) {
            case START:
                return new InitialScenario(update);
            case EVENT:
                EventContext eventContext = getEventContext(update, currentUser);
                return new EventScenarioFactory().getScenario(eventContext);
            default:
                return new UnknownScenario(update);
        }
    }


    private EventContext getEventContext(Update update, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }
}
