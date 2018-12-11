package com.travelBill.api.event;

import com.travelBill.api.core.Event;
import com.travelBill.api.core.User;
import com.travelBill.api.user.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventService(EventRepository eventRepository, UserService userService) {
        this.eventRepository = eventRepository;
        this.userService = userService;
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
        event = create(event);

        creator.setCurrentEvent(event);
        userService.save(creator);

        return event;
    }

    public Event getCurrentEvent(User user) {
        return user.getCurrentEvent();
    }
}
