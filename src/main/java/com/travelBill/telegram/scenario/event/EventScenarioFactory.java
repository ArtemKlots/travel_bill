package com.travelBill.telegram.scenario.event;

import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.common.Scenario;

public class EventScenarioFactory {
    public Scenario createScenario(EventContext eventContext) {
        EventActions type = EventActions.defineType(eventContext.update);
        if (type == null) {
            return new UnknownScenario(eventContext.update);
        }

        return selectScenario(eventContext, type);
    }

    private Scenario selectScenario(EventContext eventContext, EventActions type) {
        switch (type) {
            case SHOW_ALL:
                return new ShowEventsListScenario(eventContext);
            case CREATE:
                return new CreateEventScenario(eventContext);
            case SHOW_CURRENT:
                return new ShowCurrentEventScenario(eventContext);
            case SWITCH:
                return new SwitchCurrentEventScenario(eventContext);
            default:
                return new UnknownScenario(eventContext.update);
        }
    }
}
