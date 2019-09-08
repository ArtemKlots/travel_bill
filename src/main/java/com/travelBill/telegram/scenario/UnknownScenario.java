package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class UnknownScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        return new UnknownScenarioResponseBuider().build();
    }
}
