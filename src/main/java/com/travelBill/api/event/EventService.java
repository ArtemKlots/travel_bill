package com.travelBill.api.event;

import com.travelBill.api.core.Event;
import com.travelBill.api.core.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    public Event create(Event event) {
        return eventRepository.save(event);
    }

    public Event create(String title, User creator) {
        Event event = new Event();
        event.setTitle(title);
        event.setOwner(creator);
        return create(event);
    }
}
