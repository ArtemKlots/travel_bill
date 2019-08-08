package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.formatter.bill.BillListFormatter;
import com.travelBill.telegram.formatter.bill.LastTransactionsListFormatter;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

import java.util.List;

public class ShowLastTransactionResponse implements Response {
    private BillListFormatter formatter = new LastTransactionsListFormatter();
    List<Bill> bills;

    @Override
    public String getMessage() {
        return formatter.format(bills);
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
