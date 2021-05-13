package com.travelBill.splitBill.core.item;

import com.travelBill.splitBill.core.assigning.Assign;
import com.travelBill.splitBill.core.bill.SbBill;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "sb_item")
public class Item {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;
    //TODO: validate values below 0
    private double amount;
    private double price;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "bill_id")
    private SbBill bill;

    @OneToMany(mappedBy = "item", orphanRemoval = true)
    private List<Assign> assigns;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public SbBill getBill() {
        return bill;
    }

    public void setBill(SbBill sbBill) {
        this.bill = sbBill;
    }

    public List<Assign> getAssigns() {
        return assigns;
    }

    public void setAssigns(List<Assign> assigns) {
        this.assigns = assigns;
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
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(item.amount, amount) == 0 && Double.compare(item.price, price) == 0 && id.equals(item.id) && title.equals(item.title) && Objects.equals(createdAt, item.createdAt) && Objects.equals(updatedAt, item.updatedAt) && bill.equals(item.bill) && Objects.equals(assigns, item.assigns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, amount, price, createdAt, updatedAt, bill, assigns);
    }
}
