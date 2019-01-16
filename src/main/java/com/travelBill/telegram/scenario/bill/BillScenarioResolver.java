package com.travelBill.telegram.scenario.bill;

import org.telegram.telegrambots.meta.api.objects.Update;

public class BillScenarioResolver {
    private Update update;

    public BillScenarioResolver(Update update) {
        this.update = update;
    }

    public boolean isBillAction() {
        return isAddContributionSignal();
    }

    boolean isAddContributionSignal() {
        return update.hasMessage() && update.getMessage().getText().startsWith("/add");
    }
}
