package com.travelBill.splitBill.core.assigning;

import com.travelBill.api.core.user.User;
import com.travelBill.splitBill.core.item.Item;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sb_assigning")
public class Assigning {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private double amount;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

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

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assigning assigning = (Assigning) o;
        return Double.compare(assigning.amount, amount) == 0 && id.equals(assigning.id) && Objects.equals(createdAt, assigning.createdAt) && Objects.equals(updatedAt, assigning.updatedAt) && item.equals(assigning.item) && user.equals(assigning.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, createdAt, updatedAt, item, user);
    }
}
