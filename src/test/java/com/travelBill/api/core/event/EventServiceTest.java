package com.travelBill.api.core.event;

import com.travelBill.api.core.event.exceptions.EventAlreadyClosedException;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {
    private EventService eventService;
    private EventAccessService eventAccessServiceMock = mock(EventAccessService.class);
    private EventRepository eventRepositorySpy = spy(EventRepository.class);
    private UserService userServiceSpy = mock(UserService.class);

    private User user;
    private Event event;

    @BeforeEach
    void setupContext() {
        eventService = new EventService(eventRepositorySpy, userServiceSpy, eventAccessServiceMock);
        event = new Event();
        user = new User();

        user.setId(111L);
        user.setEvents(new ArrayList<>());

        event.setId(77L);
    }


    @Test
    void closeEvent_shouldThrowAccessDeniedException_whenUserHasNoAccessToEvent() {
        when(eventAccessServiceMock.hasAccessToEvent(user.getId(), event.getId())).thenReturn(false);

        assertThrows(AccessDeniedException.class, () -> eventService.closeEvent(event.getId(), user.getId()));
    }

    @Test
    void closeEvent_shouldCloseEvent_whenUserHasAccessToEvent() {
        event.setOpened(true);

        when(eventRepositorySpy.getOne(event.getId())).thenReturn(event);
        when(eventAccessServiceMock.hasAccessToEvent(user.getId(), event.getId())).thenReturn(true);

        assertDoesNotThrow(() -> eventService.closeEvent(event.getId(), user.getId()));
    }

    @Test
    void closeEvent_shouldMarkEventAsClosed() {
        event.setOpened(true);

        when(eventRepositorySpy.getOne(event.getId())).thenReturn(event);
        when(eventRepositorySpy.save(event)).thenReturn(event);
        when(eventAccessServiceMock.hasAccessToEvent(user.getId(), event.getId())).thenReturn(true);

        Event result = eventService.closeEvent(event.getId(), user.getId());
        assertFalse(result.isOpened());
    }

    @Test
    void closeEvent_shouldSetClosedAt() {
        event.setOpened(true);

        when(eventRepositorySpy.getOne(event.getId())).thenReturn(event);
        when(eventRepositorySpy.save(event)).thenReturn(event);
        when(eventAccessServiceMock.hasAccessToEvent(user.getId(), event.getId())).thenReturn(true);

        Event result = eventService.closeEvent(event.getId(), user.getId());
        assertNotNull(result.getClosedAt()); //todo fix it after removing LocalDateTime.now()
    }

    @Test
    void closeEvent_shouldThrowEventAlreadyClosedException_whenEventIsClosed() {
        event.setOpened(false);

        when(eventAccessServiceMock.hasAccessToEvent(user.getId(), event.getId())).thenReturn(true);
        when(eventRepositorySpy.getOne(event.getId())).thenReturn(event);

        assertThrows(EventAlreadyClosedException.class, () -> eventService.closeEvent(event.getId(), user.getId()));
    }

}
