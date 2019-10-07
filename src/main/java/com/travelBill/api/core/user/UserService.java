package com.travelBill.api.core.user;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventRepository;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public UserService(UserRepository userRepository, EventRepository eventRepository) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    /**
     * @throws AccessDeniedException   when user is not a member of an event
     * @throws EntityNotFoundException when provided incorrect user or event id
     */
    public User changeCurrentEvent(Long userId, Long eventId) {
        Event event = eventRepository.getOne(eventId);
        User user = userRepository.getOne(userId);

        List<Event> availableEvents = user.getEvents();
        List<Long> availableEventIds = availableEvents.stream().map(Event::getId).collect(Collectors.toList());
        boolean hasAccessToEvent = availableEventIds.contains(eventId);

        if (hasAccessToEvent) {
            user.setCurrentEvent(event);
            return userRepository.save(user);
        } else {
            throw new AccessDeniedException("User don't have access to the provided event");
        }
    }

    public User findUserByTelegramId(Integer id) {
        return userRepository.findUserByTelegramId(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
