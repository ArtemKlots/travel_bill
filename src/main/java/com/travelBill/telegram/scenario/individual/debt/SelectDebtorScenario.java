package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.debt.DebtService;
import com.travelBill.api.core.debt.DebtSumDto;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SelectDebtorScenario implements Scenario {
    private final DebtService debtService;
    private final UserService userService;

    public SelectDebtorScenario(DebtService debtService, UserService userService) {
        this.debtService = debtService;
        this.userService = userService;
    }


    @Override
    public Response execute(Request request) {
        List<DebtSumDto> debts = debtService.getBalanceForUser(request.user.getId());
        List<Long> debtorsIds = debts.stream()
                .map(DebtSumDto::getDebtorId)
                .collect(Collectors.toList());

        if (debtorsIds.size() == 0) {
            return new Response("You have neither debts nor debtors to send money");
        }

        List<User> lastContactedUsersAndDebtors = userService.getLastContactedUsersAnd(request.user.getId(), debtorsIds, 10);

        String message = "Your balance: \n";
        Set<Map.Entry<Long, List<DebtSumDto>>> entrySet = debts.stream().collect(Collectors.groupingBy(DebtSumDto::getDebtorId)).entrySet();
        for (Map.Entry<Long, List<DebtSumDto>> entry : entrySet) {
            message = message.concat(String.format("%s %s:\n", entry.getValue().get(0).getUserFirstName(), entry.getValue().get(0).getUserLastName()));

            for (DebtSumDto dto : entry.getValue()) {
                message = message.concat(String.format("  %s \t%s\n", dto.getCurrency(), new DecimalFormat("#,###.00").format(dto.getAmount())));
            }
            message = message.concat("\n");

        }


        Response response = new Response(message + "Select recipient:");

        response.inlineKeyboard = new DebtorListKeyboardBuilder().build(lastContactedUsersAndDebtors);

        return response;
    }
}
