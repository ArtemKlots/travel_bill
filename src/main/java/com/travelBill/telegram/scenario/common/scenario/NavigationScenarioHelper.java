package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;

import static com.travelBill.telegram.UserCommand.GO_BACK;

public class NavigationScenarioHelper {

    public boolean isNavigationBack(Request request) {
        return request.hasMessage() && request.message.equals(GO_BACK);
    }
}
