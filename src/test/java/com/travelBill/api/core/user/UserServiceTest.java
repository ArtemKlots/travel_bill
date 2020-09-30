package com.travelBill.api.core.user;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventAccessService;
import com.travelBill.api.core.event.EventRepository;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserServiceTest {
    private UserService userService;
    private UserRepository userRepository = spy(UserRepository.class);
    private EventRepository eventRepository = spy(EventRepository.class);
    private EventAccessService eventAccessServiceMock = mock(EventAccessService.class);

    @BeforeEach
    void setupContext() {
        userService = new UserService(userRepository, eventRepository, eventAccessServiceMock);

    }

    @Test
    void changeCurrentEvent_shouldThrowException_whenUserDontHaveAccessToEvent() {
        Long userId = 1L;
        Long eventId = 2L;
        Long forbiddenEventId = 3L;

        Event userEvent = new Event();
        userEvent.setId(eventId);

        Event requestedEvent = new Event();
        requestedEvent.setId(3L);


        User user = new User();
        user.setEvents(Collections.singletonList(userEvent));

        when(eventAccessServiceMock.hasAccessToEvent(userId, eventId)).thenReturn(true);
        when(eventAccessServiceMock.hasAccessToEvent(userId, forbiddenEventId)).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> userService.changeCurrentEvent(userId, forbiddenEventId));
    }

    @Test
    void changeCurrentEvent_shouldUpdateCurrentEvent_whenUserHaveAccessToIt() {
        Long userId = 1L;
        Long eventId = 2L;

        Event userEvent = new Event();
        userEvent.setId(eventId);

        User user = spy(new User());
        user.setEvents(Collections.singletonList(userEvent));

        when(eventAccessServiceMock.hasAccessToEvent(userId, eventId)).thenReturn(true);
        when(userRepository.getOne(userId)).thenReturn(user);
        when(eventRepository.getOne(eventId)).thenReturn(userEvent);

        userService.changeCurrentEvent(userId, eventId);
        verify(userRepository).save(user);
        verify(user).setCurrentEvent(userEvent);
    }
}