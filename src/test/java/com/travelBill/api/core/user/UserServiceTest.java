package com.travelBill.api.core.user;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventRepository;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository = spy(UserRepository.class);
    private EventRepository eventRepository = spy(EventRepository.class);

    @BeforeEach
    void setupContext() {
        userService = new UserService(userRepository, eventRepository);

    }

    @Test
    void changeCurrentEvent_shouldThrowException_whenUserDontHaveEvent() {
        Long userId = 1L;
        Long eventId = 2L;

        Event event = new Event();
        User user = new User();
        user.setEvents(new ArrayList<>());

        when(userRepository.getOne(userId)).thenReturn(user);
        when(eventRepository.getOne(eventId)).thenReturn(event);

        assertThrows(AccessDeniedException.class, () -> userService.changeCurrentEvent(userId, eventId));
    }

    @Test
    void changeCurrentEvent_shouldThrowException_whenUserDontHaveAccessToEvent() {
        Long userId = 1L;
        Long eventId = 2L;

        Event userEvent = new Event();
        userEvent.setId(eventId);

        Event requestedEvent = new Event();
        requestedEvent.setId(3L);


        User user = new User();
        user.setEvents(Collections.singletonList(userEvent));

        when(userRepository.getOne(userId)).thenReturn(user);
        when(eventRepository.getOne(eventId)).thenReturn(requestedEvent);

        assertThrows(AccessDeniedException.class, () -> userService.changeCurrentEvent(userId, 3L));
    }

    @Test
    void changeCurrentEvent_shouldUpdateCurrentEvent_whenUserHaveAccessToIt() {
        Long userId = 1L;
        Long eventId = 2L;

        Event userEvent = new Event();
        userEvent.setId(eventId);

        User user = spy(new User());
        user.setEvents(Collections.singletonList(userEvent));

        when(userRepository.getOne(userId)).thenReturn(user);
        when(eventRepository.getOne(eventId)).thenReturn(userEvent);

        userService.changeCurrentEvent(userId, eventId);
        verify(userRepository).save(user);
        verify(user).setCurrentEvent(userEvent);
    }
}