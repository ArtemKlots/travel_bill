package com.travelBill.api.core.bill;

import java.util.List;

public interface BillService {
    List<Bill> getAll();

    Bill save(Bill bill);
}
