package com.travelBill.api.core.event;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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

    public List<Event> getEventsByOwnerId(Long id) {
        return this.eventRepository.getEventsByOwnerId(id);
    }

    public Event findById(Long id) throws EntityNotFoundException {
        return this.eventRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Event findByTelegramChatId(Long id) throws EntityNotFoundException {
        return eventRepository.findByTelegramChatId(id);
    }

    public Event save(Event event) {
        return eventRepository.save(event);
    }

    public Event save(String title, User creator, Long chatId) {
        Event event = new Event();
        event.setTitle(title);
        event.setOwner(creator);
        event.setTelegramChatId(chatId);
        event = save(event);

        switchCurrentEvent(creator, event);

        return event;
    }

    public Event addMember(Event event, User member) {
        event.getMembers().add(member);
        return save(event);
    }

    public void switchCurrentEvent(User user, Event event) {
        user.setCurrentEvent(event);
        userService.save(user);
    }
}
