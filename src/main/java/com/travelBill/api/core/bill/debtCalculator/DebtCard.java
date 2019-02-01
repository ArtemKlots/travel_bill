package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.user.User;

import java.util.HashMap;
import java.util.Map;

public class DebtCard {
    User user;
    double debt;

    //кому должен заплатить
    Map<User, Double> accruals = new HashMap<>();

    DebtCard(User user, double debt) {
        this.user = user;
        this.debt = debt;
    }

    public double getDebt() {
        return debt;
    }

    public Map<User, Double> getAccruals() {
        return accruals;
    }
}
