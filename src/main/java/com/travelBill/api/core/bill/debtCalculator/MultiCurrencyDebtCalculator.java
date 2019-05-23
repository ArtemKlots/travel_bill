package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MultiCurrencyDebtCalculator {

    private final DebtCalculator debtCalculator;

    public MultiCurrencyDebtCalculator(DebtCalculator debtCalculator) {
        this.debtCalculator = debtCalculator;
    }

    public List<Debt> calculate(Event event) {
        List<Debt> debts = new ArrayList<>();

        Map<String, List<Bill>> billsDividedByCurrency = event.getBills().stream()
                .collect(Collectors.groupingBy(Bill::getCurrency));

        for (Map.Entry<String, List<Bill>> entry : billsDividedByCurrency.entrySet()) {
            List<Debt> result = debtCalculator.calculate(entry.getValue(), event.getMembers());
            result.forEach(debt -> debt.setCurrency(entry.getKey()));
            debts.addAll(result);
        }


        return debts;
    }
}
