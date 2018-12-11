package com.travelBill.api.event;

import com.travelBill.api.core.Event;
import com.travelBill.api.core.User;
import com.travelBill.api.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;

    public EventService(EventRepository eventRepository, UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
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
        userRepository.save(creator);

        return event;
    }
}
