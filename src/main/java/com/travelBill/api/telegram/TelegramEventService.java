package com.travelBill.api.telegram;

import com.travelBill.api.core.Event;
import com.travelBill.api.event.EventService;
import org.springframework.stereotype.Service;

@Service
public class TelegramEventService {
    private final EventService eventService;

    public TelegramEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public Event create(String eventTitle) {
        Event event = new Event();
        event.setTitle(eventTitle);
        return eventService.create(event);
    }

}
