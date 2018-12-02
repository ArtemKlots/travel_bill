package com.travelBill.api.users;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    @Column
    private Long id;
}
