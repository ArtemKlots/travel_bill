package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class IndividualScenarioFactory {
    private final ContextProvider contextProvider;

    public IndividualScenarioFactory(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    public Scenario createScenario(Update update, User currentUser) {
        EventContext eventContext = contextProvider.getEventContext(update, currentUser);
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(update);
        Scenario selectedScenario = null;

        if (eventScenarioHelper.isShowEventsSignal()) {
            selectedScenario = new ShowEventsListScenario(eventContext);
        }
        return selectedScenario;
    }

}
