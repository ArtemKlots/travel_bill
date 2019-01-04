package com.travelBill.api.core.event;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> getEventsByOwnerId(Long ownerId);
}
