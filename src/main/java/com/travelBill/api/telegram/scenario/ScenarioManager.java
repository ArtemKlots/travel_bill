package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.User;
import com.travelBill.api.event.EventService;
import com.travelBill.api.telegram.TelegramUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScenarioManager {

    private final EventService eventService;
    private final TelegramUserService telegramUserService;

    public ScenarioManager(EventService eventService, TelegramUserService telegramUserService) {
        this.eventService = eventService;
        this.telegramUserService = telegramUserService;
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


    private EventContext getEventContext(Update update, User currentUser) {
        EventContext context = new EventContext();
        context.eventService = eventService;
        context.update = update;
        context.currentUser = currentUser;
        return context;
    }
}
