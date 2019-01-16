package com.travelBill.api.core.event;

import com.travelBill.api.core.user.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface EventService {
    List<Event> getAll();

    List<Event> getEventsByOwnerId(Long id);

    Event findById(Long id) throws EntityNotFoundException;

    Event findByTelegramChatId(Long id) throws EntityNotFoundException;

    Event save(Event event);

    Event save(String title, User creator, Long chatId);

    Event addMember(Event event, User member);

    void switchCurrentEvent(User user, Event event);
}
