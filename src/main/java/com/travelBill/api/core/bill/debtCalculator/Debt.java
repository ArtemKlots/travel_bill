package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.user.User;

import java.util.Objects;

public class Debt {

    User debtor;
    User payer;
    double amount;
    private String currency;

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

        public Debt build() {
            return debt;
        }
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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