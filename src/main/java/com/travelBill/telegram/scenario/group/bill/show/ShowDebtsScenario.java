package com.travelBill.telegram.scenario.group.bill.show;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShowDebtsScenario extends AbstractBillScenario {

    public ShowDebtsScenario(BillContext billContext) {
        super(billContext);
    }


    @Override
    public Response execute() {
        Long telegramChatId = billContext.getChatId();
        Event event = billContext.eventService.findByTelegramChatId(telegramChatId);
        DebtCalculator debtCalculator = new DebtCalculator();

        List<Debt> debts = debtCalculator.calculate(event);
        Map<User, List<Debt>> debtsByDebtor = debts.stream()
                .collect(Collectors.groupingBy(Debt::getDebtor));

        ShowDebtsResponse response = new ShowDebtsResponse();
        response.debts = debtsByDebtor;

        return response;
    }

}
