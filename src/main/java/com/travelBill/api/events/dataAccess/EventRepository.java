package com.travelBill.api.events.dataAccess;

import com.travelBill.api.events.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
