package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebtCalculator {
    public List<Debt> calculate(Event event) {
        List<Debt> debts = new ArrayList<>();
        double sum = event.getBills().stream().mapToDouble(Bill::getAmount).sum();
        double coeficient = sum / event.getMembers().size();

        List<Balance> membersBalance = event.getMembers()
                .stream()
                .map(user -> getUserBalance(event, user))
                .collect(Collectors.toList());

        List<Balance> debtorsBalances = membersBalance.stream()
                .filter(balance -> balance.amount < coeficient)
                .collect(Collectors.toList());

        List<Balance> payersBalances = membersBalance.stream()
                .filter(balance -> balance.amount > coeficient)
                .collect(Collectors.toList());

        debtorsBalances.forEach(debtorBalance -> {
            payersBalances.forEach(payerBalance -> {
                Debt debt = new Debt();
                debt.debtor = debtorBalance.contributor;
                debt.payer = payerBalance.contributor;

                double debtorDebt = coeficient - debtorBalance.amount;
                double payerOverpay = payerBalance.amount - coeficient;

                if (debtorDebt == 0 || payerOverpay == 0) return;

                if (debtorDebt < payerOverpay) {
                    payerBalance.amount -= debtorDebt;
                    debtorBalance.amount += debtorDebt;
                    debt.amount += debtorDebt;
                } else {
                    payerBalance.amount -= payerOverpay;
                    debtorBalance.amount += payerOverpay;
                    debt.amount += payerOverpay;
                }
                debts.add(debt);
            });

        });
        return debts;
    }

    private Balance getUserBalance(Event event, User user) {
        List<Bill> userBills = event.getBills()
                .stream()
                .filter(bill -> bill.getUser().getId().equals(user.getId()))
                .collect(Collectors.toList());
        Balance debtCard = new Balance();
        debtCard.amount = userBills.stream().mapToDouble(Bill::getAmount).sum();
        debtCard.contributor = user;
        return debtCard;
    }
}
