package com.travelBill.telegram.scenario.common.scenario;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.individual.bill.AddBillCommandParser;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.UserCommand.SHOW_DEBTS;

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

    public boolean isDeleteBillCancellationSignal(Request request) {
        return request.hasCallbackQueryData() && request.callbackQueryData.contentEquals("cancel_bill_deleting");
    }

    public boolean isShowDebtsSignal(Request request) {
        return request.hasMessage() && request.message.equals(SHOW_DEBTS.getValue());
    }

    public boolean isShowLastBillsSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show last bills");
    }

    public boolean isShowTotalSignal(Request request) {
        return request.hasMessage() && request.message.equals("Show total");
    }
}
