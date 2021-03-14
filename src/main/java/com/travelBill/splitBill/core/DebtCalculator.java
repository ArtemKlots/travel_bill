package com.travelBill.splitBill.core;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.user.User;
import com.travelBill.splitBill.core.assigning.Assign;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DebtCalculator {
    public List<Debt> calculate(SbBill bill) {
        List<User> members = bill.getMembers();
        List<Debt> debts = new ArrayList<>();

        for (User member : members) {
            double debtAmount = 0;
            for (Item item : bill.getItems()) {
                if (item.getAssigns().size() == 0) continue;
                double assignsCount = getAssignsCount(item);
                List<Assign> memberAssigns = getAssignsForOneMember(item, member);
                //TODO: Choose better data type
                double coefficient = item.getAmount() / assignsCount;

                for (Assign assign : memberAssigns) {
                    debtAmount += assign.getAmount() * item.getPrice() * coefficient;
                }
            }

            if (debtAmount == 0) continue;

            Debt debt = createDebt(debtAmount, bill, member);
            debts.add(debt);
        }
        return debts;
    }

    double getAssignsCount(Item item) {
        //TODO: Choose better data type
        return item.getAssigns()
                .stream()
                .map(Assign::getAmount)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    Debt createDebt(double debtAmount, SbBill bill, User member) {
        return Debt.newBuilder()
                .withAmount(debtAmount)
                .withCurrency(bill.getCurrency())
                .withDebtor(member)
                .withPayer(bill.getOwner())
                .withComment(bill.getTitle())
                .build();
    }

    List<Assign> getAssignsForOneMember(Item item, User member) {
        return item.getAssigns()
                .stream()
                .filter(a -> a.getUser().getId().equals(member.getId()))
                .collect(Collectors.toList());
    }

}
