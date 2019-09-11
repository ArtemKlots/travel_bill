package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;

public interface Scenario {
    Response execute(Request request);
}
