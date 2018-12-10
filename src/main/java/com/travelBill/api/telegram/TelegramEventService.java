package com.travelBill.api.telegram;

import com.travelBill.api.core.Event;
import com.travelBill.api.event.EventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TelegramEventService {
    private final EventService eventService;

    public TelegramEventService(EventService eventService) {
        this.eventService = eventService;
    }

    public List<Event> getAll() {
        return this.eventService.getAll();
    }

    public Event create(String eventTitle) {
        Event event = new Event();
        event.setTitle(eventTitle);
        return eventService.create(event);
    }

}
