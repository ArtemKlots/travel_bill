package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class InitialScenario implements Scenario {
    private final InitialScenarioResponseBuilder initialScenarioResponseBuilder;

    public InitialScenario(InitialScenarioResponseBuilder initialScenarioResponseBuilder) {
        this.initialScenarioResponseBuilder = initialScenarioResponseBuilder;
    }

    @Override
    public Response execute(Request request) {
        return initialScenarioResponseBuilder.build();
    }


}
