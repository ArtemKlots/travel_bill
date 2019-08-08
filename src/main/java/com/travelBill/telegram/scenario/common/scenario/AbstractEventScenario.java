package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.EventContext;

public abstract class AbstractEventScenario implements Scenario {
    public EventContext eventContext;

    public AbstractEventScenario(EventContext eventContext) {
        this.eventContext = eventContext;
    }

    public abstract Response execute();
}
