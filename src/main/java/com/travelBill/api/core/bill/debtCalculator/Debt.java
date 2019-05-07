package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.user.User;

import java.util.Objects;

public class Debt {

    public User debtor;
    public User payer;
    public double amount;


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
