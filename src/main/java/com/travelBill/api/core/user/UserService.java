package com.travelBill.api.core.user;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventAccessService;
import com.travelBill.api.core.event.EventRepository;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final EventAccessService eventAccessService;

    public UserService(UserRepository userRepository,
                       EventRepository eventRepository,
                       EventAccessService eventAccessService) {
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.eventAccessService = eventAccessService;
    }

    /**
     * @throws AccessDeniedException   when user is not a member of an event
     * @throws EntityNotFoundException when provided incorrect user or event id
     */
    public User changeCurrentEvent(Long userId, Long eventId) {
        boolean hasAccessToEvent = eventAccessService.hasAccessToEvent(userId, eventId);

        if (hasAccessToEvent) {
            Event event = eventRepository.getOne(eventId);
            User user = userRepository.getOne(userId);

            user.setCurrentEvent(event);
            return userRepository.save(user);
        } else {
            throw new AccessDeniedException("User don't have access to the provided event");
        }
    }

    public User findUserByTelegramId(Integer id) {
        return userRepository.findUserByTelegramId(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public List<User> getAllContactedUsers(Long userId) {
        return userRepository.getAllContactedUsers(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
