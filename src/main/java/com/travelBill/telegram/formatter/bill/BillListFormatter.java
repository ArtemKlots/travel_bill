package com.travelBill.telegram.formatter.bill;

import com.travelBill.api.core.bill.Bill;

import java.util.List;

public interface BillListFormatter {
    String format(List<Bill> bills);
}
