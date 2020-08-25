package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;

import static com.travelBill.telegram.UserCommand.SEND_MONEY;

public class DebtScenarioHelper {

    public boolean isSelectDebtorRequest(Request request) {
        return request.hasMessage() && request.message.equals(SEND_MONEY);
    }


    public boolean isRequestAmountRequest(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("send_money_to_");
    }

    public boolean isAllContactsRequest(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.equals("select_debtor-all_contacts");
    }
}
