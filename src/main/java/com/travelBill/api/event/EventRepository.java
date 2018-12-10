package com.travelBill.api.event;

import com.travelBill.api.core.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
