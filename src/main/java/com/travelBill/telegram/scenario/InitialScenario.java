package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public class InitialScenario implements Scenario {

    @Override
    public Response execute() {
        return new InitialScenarioResponseBuider().build();
    }


}
