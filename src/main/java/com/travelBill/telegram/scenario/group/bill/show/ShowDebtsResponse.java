package com.travelBill.telegram.scenario.group.bill.show;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class ShowDebtsResponse implements Response {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public Map<User, List<Debt>> debts;

    @Override
    public String getMessage() {
        //todo Refactor and extract to formatter
        StringBuffer report = new StringBuffer();

        if (debts.size() > 0) {
            for (Map.Entry<User, List<Debt>> entry : debts.entrySet()) {
                report.append(String.format("%s debts: \n",
                        entry.getKey().getFullName()));

                entry.getValue().forEach(debt -> {
                    report.append(String.format(" -- %s %s to %s \n", decimalFormat.format(debt.getAmount()), debt.getCurrency(), debt.getPayer().getFullName()));
                    report.append("\n");
                });
            }
        } else {
            report.append("There are no debts between you");
        }
        return report.toString();
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
