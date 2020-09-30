package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.DebtService;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import static com.travelBill.Icons.MONEY_BAG;
import static com.travelBill.Icons.MONEY_WINGS;
import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;

@Service
public class ShowHistoryScenario implements Scenario {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, HH:mm");
    private final DebtService debtService;

    public ShowHistoryScenario(DebtService debtService) {
        this.debtService = debtService;
    }

    @Override
    public Response execute(Request request) {
        List<Debt> debts = debtService.getHistoryForUser(request.user.getId());
        Collections.reverse(debts);

        Response response = new Response(MARKDOWN);
        StringBuilder messageBuilder = new StringBuilder();

        debts.forEach(debt -> {
            if (debt.getPayer().getId().equals(request.user.getId())) {
                messageBuilder.append(String.format("%s %s \n*%s %s* to *%s* \nComment: *%s* \n\n", MONEY_WINGS, debt.getCreatedAt().format(formatter), debt.getAmount(), debt.getCurrency(), debt.getDebtor().getFullName(), debt.getComment()));
            } else {
                messageBuilder.append(String.format("%s %s \n*%s %s* from *%s* \nComment: *%s* \n\n", MONEY_BAG, debt.getCreatedAt().format(formatter), debt.getAmount(), debt.getCurrency(), debt.getPayer().getFullName(), debt.getComment()));
            }
        });
        response.message = messageBuilder.toString();
        return response;
    }
}
