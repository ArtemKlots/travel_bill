package com.travelBill.api.core.debt;

import com.travelBill.api.core.user.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "debtor_id")
    private User debtor;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "payer_id")
    private User payer;

    @Column
    private double amount;

    @Column
    private String currency;

    @Column
    private String comment;

    @Column
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public static DebtBuilder newBuilder() {
        return new Debt().new DebtBuilder();
    }

    public class DebtBuilder {
        Debt debt = new Debt();

        public DebtBuilder withDebtor(User user) {
            debt.debtor = user;
            return this;
        }

        public DebtBuilder withPayer(User payer) {
            debt.payer = payer;
            return this;
        }

        public DebtBuilder withAmount(double amount) {
            debt.amount = amount;
            return this;
        }

        public DebtBuilder withCurrency(String currency) {
            debt.currency = currency;
            return this;
        }

        public DebtBuilder withComment(String comment) {
            debt.comment = comment;
            return this;
        }

        public Debt build() {
            return debt;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getDebtor() {
        return debtor;
    }

    public void setDebtor(User debtor) {
        this.debtor = debtor;
    }

    public User getPayer() {
        return payer;
    }

    public void setPayer(User payer) {
        this.payer = payer;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Debt)) return false;
        Debt debt = (Debt) o;
        return Double.compare(debt.amount, amount) == 0 &&
                Objects.equals(debtor, debt.debtor) &&
                Objects.equals(payer, debt.payer) &&
                Objects.equals(currency, debt.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtor, payer, amount, currency);
    }
}
