package com.travelBill.api.core.event;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventAccessService {

    private final UserRepository userRepository;

    public EventAccessService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public boolean hasAccessToEvent(Long userId, Long eventId) {
        User user = userRepository.getOne(userId);

        List<Event> availableEvents = user.getEvents();
        List<Long> availableEventIds = availableEvents.stream().map(Event::getId).collect(Collectors.toList());
        return availableEventIds.contains(eventId);
    }
}
