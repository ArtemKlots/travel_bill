package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.user.User;

import java.util.Objects;

public class Debt {

    public User debtor;
    public User payer;
    public double amount;

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

        public Debt build() {
            return debt;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Debt)) return false;
        Debt debt = (Debt) o;
        return Double.compare(debt.amount, amount) == 0 &&
                debtor.equals(debt.debtor) &&
                payer.equals(debt.payer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(debtor, payer, amount);
    }
}