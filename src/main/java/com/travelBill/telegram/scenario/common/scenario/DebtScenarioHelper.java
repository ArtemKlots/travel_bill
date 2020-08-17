package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;

public class DebtScenarioHelper {

    public boolean isSelectDebtorRequest(Request request) {
        return request.hasMessage() && request.message.equals("Send money");
    }


    public boolean isRequestAmountRequest(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("send_money_to_");
    }
}
