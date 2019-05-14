package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.event.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MultiCurrencyDebtCalculator {

    private final DebtCalculator debtCalculator;

    public MultiCurrencyDebtCalculator(DebtCalculator debtCalculator) {
        this.debtCalculator = debtCalculator;
    }

    public List<Debt> calculate(Event event) {


        return new ArrayList<>();
    }
}
