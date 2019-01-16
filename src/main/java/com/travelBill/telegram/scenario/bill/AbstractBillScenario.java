package com.travelBill.telegram.scenario.bill;

import com.travelBill.telegram.scenario.common.Scenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public abstract class AbstractBillScenario implements Scenario {
    BillContext billContext;

    AbstractBillScenario(BillContext billContext) {
        this.billContext = billContext;
    }

    public abstract SendMessage createMessage();
}
