package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.individual.bill.AddBillCommandParser;
import org.springframework.stereotype.Service;

@Service
public class BillScenarioHelper {

    public boolean isContribution(Request request) {
        return AddBillCommandParser.isContribution(request.message);
    }

    public boolean isDeleteBillRequestSignal(Request request) {
        return request.hasMessage() && request.message.equals("Delete bill");
    }

    public boolean isDeleteBillConfirmationSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("delete_bill");
    }

    public boolean isShowDebtsSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show debts");
    }

    public boolean isShowLastBillsSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show last bills");
    }

    public boolean isShowTotalSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show total");
    }
}
