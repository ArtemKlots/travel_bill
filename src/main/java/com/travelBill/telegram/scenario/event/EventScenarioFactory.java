package com.travelBill.telegram.scenario.event;

public class EventScenarioFactory {
    public AbstractEventScenario getScenario(EventContext eventContext) {

        switch (EventActions.defineType(eventContext.update)) {
            case SHOW_ALL:
                return new ShowEventsListScenario(eventContext);
            case CREATE:
                return new CreateEventScenario(eventContext);
            case SHOW_CURRENT:
                return new ShowCurrentEventScenario(eventContext);
            case SWITCH:
                return new SwitchCurrentEventScenario(eventContext);
            default:
                throw new RuntimeException("Cant choose a scenario");
        }
    }
}
