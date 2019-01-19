package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.scenario.common.context.EventContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractEventScenario implements Scenario {
    public EventContext eventContext;

    public AbstractEventScenario(EventContext eventContext) {
        this.eventContext = eventContext;
    }

    public abstract SendMessage createMessage();
}
