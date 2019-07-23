package com.travelBill.api.core.bill;

import java.util.List;

public interface BillService {
    List<Bill> getAll();

    Bill findById(Long id);

    Bill save(Bill bill);

    void deleteById(Long id);

    List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id);

    List<Bill> selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId);
}
