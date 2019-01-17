package com.travelBill.telegram.scenario.bill;

import com.travelBill.telegram.scenario.common.Scenario;

public class BillScenarioFactory {
    public Scenario createScenario(BillContext billContext) {
        BillScenarioResolver billScenarioResolver = new BillScenarioResolver(billContext.update);
        Scenario selectedScenario = null;

        if (billScenarioResolver.isAddBillSignal()) {
            selectedScenario = new AddBillScenario(billContext);
        }

        return selectedScenario;
    }
}
