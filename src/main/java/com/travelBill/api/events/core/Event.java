package com.travelBill.api.events.core;

import com.travelBill.api.transactions.core.TransactionList;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private TransactionList transactionList;

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

    public TransactionList getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(TransactionList transactionList) {
        this.transactionList = transactionList;
    }
}
