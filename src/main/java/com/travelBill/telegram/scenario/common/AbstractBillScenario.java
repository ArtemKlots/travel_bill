package com.travelBill.telegram.scenario.common;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public abstract class AbstractBillScenario implements Scenario {
    public BillContext billContext;

    public AbstractBillScenario(BillContext billContext) {
        this.billContext = billContext;
    }

    public abstract Response execute();
}
