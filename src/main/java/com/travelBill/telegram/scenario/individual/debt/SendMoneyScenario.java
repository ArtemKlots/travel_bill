package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.DebtService;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.bill.AddBillCommandParser;
import com.travelBill.telegram.user.state.State;
import com.travelBill.telegram.user.state.UserState;
import com.travelBill.telegram.user.state.UserStateService;
import org.springframework.stereotype.Service;

@Service
public class SendMoneyScenario implements Scenario {
    private final DebtService debtService;
    private final UserService userService;
    private final UserStateService userStateService;

    public SendMoneyScenario(DebtService debtService, UserService userService, UserStateService userStateService) {
        this.debtService = debtService;
        this.userService = userService;
        this.userStateService = userStateService;
    }

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        Bill bill = AddBillCommandParser.parse(request.message);
        long debtor_id = Long.parseLong(userStateService.get(request.user.getId()).getArgument());
        User debtor = userService.findById(debtor_id);

        Debt debt = Debt.newBuilder()
                .withAmount(bill.getAmount())
                .withCurrency(bill.getCurrency())
                .withDebtor(debtor)
                .withPayer(request.user)
                .withComment(bill.getPurpose())
                .build();

        debtService.add(debt);
        userStateService.change(new UserState(request.user, State.START));
        response.message = String.format("Done! *%s %s* was successfully sent to *%s*", debt.getAmount(), debt.getCurrency(), debt.getDebtor().getFullName());
        response.parseMode = ParseMode.MARKDOWN;

        return response;
    }
}
