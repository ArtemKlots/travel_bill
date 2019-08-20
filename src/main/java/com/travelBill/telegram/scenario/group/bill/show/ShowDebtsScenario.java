package com.travelBill.telegram.scenario.group.bill.show;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ShowDebtsScenario implements Scenario {
    private final EventService eventService;

    public ShowDebtsScenario(EventService eventService) {

        this.eventService = eventService;
    }

    @Override
    public Response execute(Request request) {
        Long telegramChatId = request.chatId;
        Event event = eventService.findByTelegramChatId(telegramChatId);
        DebtCalculator debtCalculator = new DebtCalculator();

        List<Debt> debts = debtCalculator.calculate(event);
        Map<User, List<Debt>> debtsByDebtor = debts.stream()
                .collect(Collectors.groupingBy(Debt::getDebtor));

        ShowDebtsResponseBuilder responseBuilder = new ShowDebtsResponseBuilder();
        responseBuilder.debts = debtsByDebtor;

        return responseBuilder.build();
    }

}
