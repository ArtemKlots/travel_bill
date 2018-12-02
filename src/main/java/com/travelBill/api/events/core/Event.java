package com.travelBill.api.events.core;

import com.travelBill.api.transactions.core.TransactionList;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column
    private String title;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "event")
    private List<TransactionList> transactionList;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

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

    public List<TransactionList> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<TransactionList> transactionList) {
        this.transactionList = transactionList;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
