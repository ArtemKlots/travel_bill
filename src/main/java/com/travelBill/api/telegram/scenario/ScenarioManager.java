package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.User;
import com.travelBill.api.event.EventService;
import com.travelBill.api.telegram.scenario.event.*;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScenarioManager {

    private final EventService eventService;

    public ScenarioManager(EventService eventService) {
        this.eventService = eventService;
    }

    public SendMessage performInitialScenatio(Update update) {
        return InitialScenario.perform(update);
    }

    public SendMessage createEvent(Update update, User currentUser) {
        EventContext eventContext = getEventContext(update, currentUser);
        return CreateEventScenario.perform(eventContext);
    }

    public SendMessage getAllEvents(Update update, User currentUser) {
        EventContext eventContext = getEventContext(update, currentUser);
        return ShowEventsListScenario.perform(eventContext);
    }

    public SendMessage getCurrentEvent(Update update, User currentUser) {
        EventContext eventContext = getEventContext(update, currentUser);
        return ShowCurrentEventScenario.perform(eventContext);
    }

    public SendMessage switchCurrentEvent(Update update, User currentUser) {
        EventContext eventContext = getEventContext(update, currentUser);
        return SwitchCurrentEventScenario.perform(eventContext);
    }


    private EventContext getEventContext(Update update, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }
}
