package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.event.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MultiCurrencyDebtCalculator {

    private final DebtCalculator debtCalculator;

    public MultiCurrencyDebtCalculator(DebtCalculator debtCalculator) {
        this.debtCalculator = debtCalculator;
    }

    public List<Debt> calculate(Event event) {
        List<Debt> debts = new ArrayList<>();

        event.getBills().forEach(bill -> {
            debts.addAll(debtCalculator.calculate(Collections.singletonList(bill), event.getMembers()));
        });


        return debts;
    }
}
