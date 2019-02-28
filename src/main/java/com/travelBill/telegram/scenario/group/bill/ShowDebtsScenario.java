package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.debtCalculator.DeprecatedDebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
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
        List<Bill> bills = event.getBills();
        List<User> users = event.getMembers();
        DeprecatedDebtCalculator debtCalculator = new DeprecatedDebtCalculator();

        Map<String, List<Bill>> billsDividedByCurrency = bills.stream()
                .collect(Collectors.groupingBy(Bill::getCurrency));

        StringBuilder report = new StringBuilder();
        for (Map.Entry<String, List<Bill>> entry : billsDividedByCurrency.entrySet()) {
            Map<User, Map<User, Double>> debts = debtCalculator.getDebts(users, entry.getValue());
            String reportChunk = makeDebtReport(debts, entry.getKey());
            report.append(reportChunk);
        }


        return new SendMessage()
                .setChatId(billContext.getChatId())
                .setText(report.toString());
    }

    private String makeDebtReport(Map<User, Map<User, Double>> debts, String currency) {
        StringBuffer report = new StringBuffer();

        debts.forEach((debtor, values) -> {
            report.append(String.format("%s %s debts (%s): \n", debtor.getFirstName(), debtor.getLastName(), currency));

            values.forEach((creditor, amount) -> {
                report.append(String.format(" -- %s %s to the %s %s \n", decimalFormat.format(amount), currency, creditor.getFirstName(), creditor.getLastName()));
            });
            report.append("\n");
        });

        return report.toString();
    }
}
