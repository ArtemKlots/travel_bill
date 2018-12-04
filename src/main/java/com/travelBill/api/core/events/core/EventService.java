package com.travelBill.api.core.events.core;

import com.travelBill.api.core.events.dataAccess.EventDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final EventDataAccessService eventDataAccessService;

    @Autowired
    public EventService(EventDataAccessService eventDataAccessService) {
        this.eventDataAccessService = eventDataAccessService;
    }

    public List<Event> getAll() {
        return this.eventDataAccessService.getAll();
    }
}
