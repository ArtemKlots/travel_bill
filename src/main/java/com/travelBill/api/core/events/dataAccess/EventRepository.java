package com.travelBill.api.core.events.dataAccess;

import com.travelBill.api.core.events.core.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
