package com.travelBill.api.core.bill.debtCalculator;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DebtCalculator {
    public List<Debt> calculate(Event event) {
        List<Debt> debts = new ArrayList<>();
        double allBillsSum = event.getBills().stream().mapToDouble(Bill::getAmount).sum();
        double averageContribution = allBillsSum / event.getMembers().size();

        List<Balance> membersBalance = getAllMembersBalance(event);
        List<Balance> debtorsBalances = getAllDebtorsBalances(averageContribution, membersBalance);
        List<Balance> payersBalances = getAllPayersBalances(averageContribution, membersBalance);

        debtorsBalances.forEach(debtorBalance -> {
            payersBalances.forEach(payerBalance -> {
                Debt debt = new Debt();
                debt.debtor = debtorBalance.contributor;
                debt.payer = payerBalance.contributor;

                double debtorDebt = averageContribution - debtorBalance.amount;
                double payerOverpay = payerBalance.amount - averageContribution;

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

    private List<Balance> getAllMembersBalance(Event event) {
        return event.getMembers()
                .stream()
                .map(user -> getUserBalance(event, user))
                .collect(Collectors.toList());
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

    private List<Balance> getAllDebtorsBalances(double averageContribution, List<Balance> membersBalance) {
        return membersBalance.stream()
                .filter(balance -> balance.amount < averageContribution)
                .collect(Collectors.toList());
    }

    private List<Balance> getAllPayersBalances(double averageContribution, List<Balance> membersBalance) {
        return membersBalance.stream()
                .filter(balance -> balance.amount > averageContribution)
                .collect(Collectors.toList());
    }
}
