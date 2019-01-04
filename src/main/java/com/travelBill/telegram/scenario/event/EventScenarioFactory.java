package com.travelBill.telegram.scenario.event;

import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.Scenario;

import static java.util.Objects.isNull;

public class EventScenarioFactory {
    public Scenario createScenario(EventContext eventContext) {
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(eventContext.update);
        Scenario selectedScenario = null;

        if (eventScenarioHelper.isShowEventsSignal()) {
            selectedScenario = new ShowEventsListScenario(eventContext);
        }

        if (eventScenarioHelper.isCreateEventSignal()) {
            selectedScenario = new CreateEventScenario(eventContext);
        }

        if (eventScenarioHelper.isShowCurrentEventSignal()) {
            selectedScenario = new ShowCurrentEventScenario(eventContext);
        }

        if (eventScenarioHelper.isSwitchingEventSignal()) {
            selectedScenario = new SwitchCurrentEventScenario(eventContext);
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario(eventContext.update);
        }

        return selectedScenario;
    }
}
