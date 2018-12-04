package com.travelBill.api.core.events.dataAccess;

import com.travelBill.api.core.events.core.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventDataAccessService {

    private final EventRepository eventRepository;

    @Autowired
    public EventDataAccessService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
