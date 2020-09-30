package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.individual.bill.AddBillCommandParser;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.UserCommand.*;

@Service
public class BillScenarioHelper {

    public boolean isContribution(Request request) {
        return AddBillCommandParser.isContribution(request.message);
    }

    public boolean isDeleteBillRequestSignal(Request request) {
        return request.hasMessage() && request.message.equals(DELETE_BILL);
    }

    public boolean isDeleteBillConfirmationSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.startsWith("delete_bill");
    }

    public boolean isDeleteBillCancellationSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.contentEquals("cancel_bill_deleting");
    }

    public boolean isShowDebtsSignal(Request request) {
        return request.hasMessage() && request.message.equals(EVENT_DEBTS);
    }

    public boolean isShowLastBillsSignal(Request request) {
        return request.hasMessage() && request.message.equals(RECENT_BILLS);
    }

    public boolean isShowTotalSignal(Request request) {
        return request.hasMessage() && request.message.equals(EVENT_STATISTIC);
    }
}
