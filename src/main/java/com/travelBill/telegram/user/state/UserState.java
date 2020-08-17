package com.travelBill.telegram.user.state;

import com.travelBill.api.core.user.User;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_state")
public class UserState {
    @Id
    private long userId;

    @OneToOne
    @MapsId
    private User user;

    @Column
    @Enumerated(EnumType.STRING)
    private State state;

    @Column
    private String argument;

    public UserState() {
    }

    public UserState(User user, State state) {
        this.userId = user.getId();
        this.user = user;
        this.state = state;
    }

    public UserState(User user, State state, String argument) {
        this.userId = user.getId();
        this.user = user;
        this.state = state;
        this.argument = argument;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.userId = user.getId();
        this.user = user;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getArgument() {
        return argument;
    }

    public void setArgument(String argument) {
        this.argument = argument;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserState)) return false;
        UserState userState = (UserState) o;
        return user.equals(userState.user) &&
                state == userState.state &&
                Objects.equals(argument, userState.argument);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, state, argument);
    }
}
