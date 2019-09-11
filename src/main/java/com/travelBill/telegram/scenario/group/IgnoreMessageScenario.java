package com.travelBill.telegram.scenario.group;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

public class IgnoreMessageScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        return new Response();
    }
}
