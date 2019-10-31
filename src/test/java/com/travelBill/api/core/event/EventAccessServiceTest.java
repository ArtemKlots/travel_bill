package com.travelBill.api.core.event;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventAccessServiceTest {

    private EventAccessService eventAccessService;
    private EventRepository eventRepositoryMock = mock(EventRepository.class);
    private UserRepository userRepositoryMock = mock(UserRepository.class);


    private Event event;
    private User user;


    @BeforeEach
    void setupContext() {
        eventAccessService = new EventAccessService(userRepositoryMock);
        event = new Event();
        user = new User();

        user.setId(111L);
        user.setEvents(new ArrayList<>());

        event.setId(77L);
    }

    @Test
    void hasAccessToEvent_shouldReturnFalse_whenUserDontHaveAnyEvent() {
        when(userRepositoryMock.getOne(user.getId())).thenReturn(user);
        assertFalse(eventAccessService.hasAccessToEvent(user.getId(), event.getId()));
    }

    @Test
    void hasAccessToEvent_shouldReturnFalse_whenUserIsNotMemberOfEvent() {
        Event forbiddenEvent = new Event();
        forbiddenEvent.setId(11L);
        user.setEvents(List.of(event));

        when(userRepositoryMock.getOne(user.getId())).thenReturn(user);
        assertFalse(eventAccessService.hasAccessToEvent(user.getId(), forbiddenEvent.getId()));
    }

    @Test
    void hasAccessToEvent_shouldReturnTrue_whenUserIsMemberOfEvent() {
        user.getEvents().add(event);

        when(userRepositoryMock.getOne(user.getId())).thenReturn(user);

        assertTrue(eventAccessService.hasAccessToEvent(user.getId(), event.getId()));
    }

}