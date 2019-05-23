package com.travelBill.api.core.bill;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String purpose;
    private String currency;
    private double amount;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bill)) return false;
        Bill bill = (Bill) o;
        return Double.compare(bill.getAmount(), getAmount()) == 0 &&
                Objects.equals(getId(), bill.getId()) &&
                Objects.equals(getPurpose(), bill.getPurpose()) &&
                Objects.equals(getCurrency(), bill.getCurrency()) &&
                Objects.equals(getCreatedAt(), bill.getCreatedAt()) &&
                Objects.equals(getUpdatedAt(), bill.getUpdatedAt()) &&
                Objects.equals(getEvent(), bill.getEvent()) &&
                Objects.equals(getUser(), bill.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPurpose(), getCurrency(), getAmount(), getCreatedAt(), getUpdatedAt(), getEvent(), getUser());
    }
}
