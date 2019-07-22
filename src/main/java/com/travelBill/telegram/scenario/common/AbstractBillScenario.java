package com.travelBill.telegram.scenario.common;

import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractBillScenario implements Scenario {
    public BillContext billContext;

    public AbstractBillScenario(BillContext billContext) {
        this.billContext = billContext;
    }

    public abstract SendMessage createMessage();
}
