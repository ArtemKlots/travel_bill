package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;

public class NavigationScenarioHelper {

    public boolean isNavigationBack(Request request) {
        return request.hasMessage() && request.message.contains("Back to the previous menu");
    }
}
