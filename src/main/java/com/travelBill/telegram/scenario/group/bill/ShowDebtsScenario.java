package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowDebtsScenario extends AbstractBillScenario {
    private static DecimalFormat decimalFormat = new DecimalFormat(".##");

    public ShowDebtsScenario(BillContext billContext) {
        super(billContext);
    }


    @Override
    public SendMessage createMessage() {
        Long telegramChatId = billContext.update.getMessage().getChatId();
        Event event = billContext.eventService.findByTelegramChatId(telegramChatId);
        DebtCalculator debtCalculator = new DebtCalculator();

        List<Debt> debts = debtCalculator.calculate(event);
        Map<User, List<Debt>> debtsByDebtor = debts.stream()
                .collect(Collectors.groupingBy(Debt::getDebtor));

        String report = makeDebtReport(debtsByDebtor);


        return new SendMessage()
                .setChatId(billContext.getChatId())
                .setText(report);
    }

    //todo Refactor and extract to formatter
    private String makeDebtReport(Map<User, List<Debt>> debts) {
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
}
