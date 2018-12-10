package com.travelBill.api.telegram.scenario;

import com.travelBill.api.event.EventService;
import com.travelBill.api.telegram.TelegramEventService;
import com.travelBill.api.telegram.TelegramUserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class ScenarioManager {

    private final EventService eventService;
    private final TelegramUserService telegramUserService;
    private final TelegramEventService telegramEventService;

    public ScenarioManager(EventService eventService, TelegramUserService telegramUserService, TelegramEventService telegramEventService) {
        this.eventService = eventService;
        this.telegramUserService = telegramUserService;
        this.telegramEventService = telegramEventService;
    }

    public SendMessage performInitialScenatio(Update update) {
        return InitialScenario.perform(update);
    }

    public SendMessage createEvent(Update update) {
        EventContext eventContext = getEventContext(update);
        return CreateEventScenario.perform(eventContext);
    }

    public SendMessage getAllEvents(Update update) {
        EventContext eventContext = getEventContext(update);
        return ShowEventsListScenario.perform(eventContext);
    }


    private EventContext getEventContext(Update update) {
        return new EventContext(telegramEventService, update);
    }
}
