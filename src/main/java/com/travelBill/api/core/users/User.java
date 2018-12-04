package com.travelBill.api.core.users;

import com.travelBill.api.core.events.core.Event;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToMany
    @JoinTable(name = "events_users",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> events;
}
