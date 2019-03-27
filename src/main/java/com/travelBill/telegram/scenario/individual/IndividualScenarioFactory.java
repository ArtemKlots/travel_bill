package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.formatter.bill.LastTransactionsListFormatter;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.NewEventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

@Service
public class IndividualScenarioFactory {
    private final ContextProvider contextProvider;
    private final NewEventScenarioHelper eventScenarioHelper;
    private final BillScenarioHelper billScenarioHelper;

    @Autowired
    public IndividualScenarioFactory(ContextProvider contextProvider,
                                     NewEventScenarioHelper eventScenarioHelper,
                                     BillScenarioHelper billScenarioHelper) {
        this.contextProvider = contextProvider;
        this.eventScenarioHelper = eventScenarioHelper;
        this.billScenarioHelper = billScenarioHelper;
    }

    public Scenario createScenario(Update update, User currentUser) throws ScenarioNotFoundException {
        EventContext eventContext = contextProvider.getEventContext(update, currentUser);
        BillContext billContext = contextProvider.getBillContext(update, currentUser);

        Scenario selectedScenario = null;

        if (eventScenarioHelper.isShowEventsSignal(update)) {
            selectedScenario = new ShowEventsListScenario(eventContext);
        }

        if (billScenarioHelper.isShowLastTransactionsSignal(update)) {
            selectedScenario = new ShowLastTransactionsScenario(billContext, new LastTransactionsListFormatter());
        }

        if (isNull(selectedScenario)) {
            throw new ScenarioNotFoundException();
        }

        return selectedScenario;
    }

}
