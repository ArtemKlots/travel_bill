package com.travelBill.api.core.debt.debtCalculator;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class DeprecatedDebtCalculator {

    public Map<User, Map<User, Double>> getDebts(List<User> users, List<Bill> bills) {
        double amount = bills.stream().mapToDouble(Bill::getAmount).sum();
        double commonContribution = amount / users.size();

        List<DebtCard> debtCards = new ArrayList<>();
        for (User user : users) {
            double paidByUser = bills.stream()
                    .filter(bill -> bill.getUser().getId().equals(user.getId()))
                    .mapToDouble(Bill::getAmount)
                    .sum();

            double debt = commonContribution - paidByUser;
            DebtCard debtCard = new DebtCard(user, debt);
            debtCards.add(debtCard);
        }

        List<DebtCard> debtorsDebtCards = debtCards.stream()
                .filter(d -> d.debt > 0)
                .sorted(Comparator.comparingDouble(DebtCard::getDebt))
                .collect(Collectors.toList());

        List<DebtCard> payersDebtCards = debtCards.stream()
                .filter(d -> d.debt < 0)
                .sorted(Comparator.comparingDouble(DebtCard::getDebt).reversed())
                .collect(Collectors.toList());

        for (DebtCard debtorDebtCard : debtorsDebtCards) {
            for (DebtCard payerDebtCard : payersDebtCards) {
                if (debtorDebtCard.getDebt() + payerDebtCard.getDebt() >= 0) {
                    debtorDebtCard.accruals.put(payerDebtCard.user, Math.abs(payerDebtCard.debt));
                    payerDebtCard.debt -= payerDebtCard.debt;
                    debtorDebtCard.debt -= payerDebtCard.debt;
                } else if (debtorDebtCard.getDebt() + payerDebtCard.getDebt() < 0) {
                    debtorDebtCard.accruals.put(payerDebtCard.user, Math.abs(debtorDebtCard.debt));
                    payerDebtCard.debt += debtorDebtCard.debt;
                    debtorDebtCard.debt -= debtorDebtCard.debt;
                }
            }
        }

        Map<User, Map<User, Double>> result = new HashMap<>();
        debtorsDebtCards.forEach(debtCard -> result.put(debtCard.user, debtCard.accruals));
        return result;
    }
}
