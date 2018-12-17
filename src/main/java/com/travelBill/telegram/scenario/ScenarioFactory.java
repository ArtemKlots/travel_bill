package com.travelBill.telegram.scenario;

import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.event.EventActions;
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

    //todo refacor
    public Scenario getScenario(Update update, User currentUser) {
        if (EventActions.isEventAction(update)) {
            EventContext eventContext = getEventContext(update, currentUser);
            return new EventScenarioFactory().getScenario(eventContext);
        } else {
            boolean isStartSignal = update.getMessage().getText().equals("/start");

            if (isStartSignal) {
                //todo fix it
                InitialScenario.perform(update);
            }
            return null;
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
