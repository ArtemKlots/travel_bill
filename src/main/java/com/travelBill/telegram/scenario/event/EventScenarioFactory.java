package com.travelBill.telegram.scenario.event;

import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

import static java.util.Objects.isNull;

public class EventScenarioFactory {
    public Scenario createScenario(EventContext eventContext) {
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(eventContext.update);
        Scenario selectedScenario = null;

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario(eventContext.update);
        }

        return selectedScenario;
    }
}
