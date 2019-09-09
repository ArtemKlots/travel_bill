package com.travelBill.telegram.formatter.bill;

import com.travelBill.api.core.bill.Bill;

import java.util.List;

public class LastBillsListFormatter implements BillListFormatter {
    private final String HEADER = "Here are your last bills:\n";

    public String format(List<Bill> bills) {
        StringBuilder stringBuilder = new StringBuilder(HEADER);

        for (Bill bill : bills) {
            String row = String.format("%1.2f %s %s", bill.getAmount(), bill.getCurrency(), bill.getPurpose());
            stringBuilder.append(row).append("\n");
        }

        return stringBuilder.toString();
    }
}
