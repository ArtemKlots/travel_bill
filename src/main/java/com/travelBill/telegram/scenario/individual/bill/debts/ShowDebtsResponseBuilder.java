package com.travelBill.telegram.scenario.individual.bill.debts;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import static com.travelBill.Icons.RED_EXCLAMATION_MARK;

public class ShowDebtsResponseBuilder implements ResponseBuilder {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##");
    public Map<User, List<Debt>> debts;
    public Event event;


    @Override
    public Response build() {
        Response response = new Response();
        response.message = getMessage();
        response.parseMode = ParseMode.MARKDOWN;
        return response;
    }

    public String getMessage() {
        //todo Refactor and extract to formatter
        StringBuffer report = new StringBuffer();

        if (debts.size() > 0) {
            if (!event.isOpened()) {
                report.append("\n");
                report.append(RED_EXCLAMATION_MARK + "*DO NOT TRY TO FIND YOUR DEBTS HERE*" + RED_EXCLAMATION_MARK + " This section is only for historical reason.\n\nYou can find final debts in \"1:1 -> send money\" section");
                report.append("\n");
            }

            for (Map.Entry<User, List<Debt>> entry : debts.entrySet()) {
                report.append(String.format("%s debts: \n",
                        entry.getKey().getFullName()));

                entry.getValue().forEach(debt -> {
                    report.append(String.format(" -- %s %s to %s \n", decimalFormat.format(debt.getAmount()), debt.getCurrency(), debt.getPayer().getFullName()));
                    report.append("\n");
                });
            }
            if (!event.isOpened()) {
                report.append("\n");
                report.append(RED_EXCLAMATION_MARK + "*DO NOT TRY TO FIND YOUR DEBTS HERE*" + RED_EXCLAMATION_MARK + " This section is only for historical reason.\n\nYou can find final debts in \"1:1 -> send money\" section");
                report.append("\n");
            }
        } else {
            report.append("There are no debts between you");
        }
        return report.toString();
    }

}
