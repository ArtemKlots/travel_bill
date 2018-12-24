package com.travelBill.telegram.scenario.event;

import com.travelBill.telegram.scenario.common.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractEventScenario implements Scenario {
    EventContext eventContext;

    AbstractEventScenario(EventContext eventContext) {
        this.eventContext = eventContext;
    }

    public abstract SendMessage createMessage();
}
