package com.travelBill.api.core.bill;

import com.travelBill.api.core.user.User;

import java.util.List;

// TODO: 30.07.19 get rid from the interface
public interface BillService {
    List<Bill> getAll();

    Bill findById(Long id);

    Bill save(Bill bill);

    void delete(Bill bill, User owner);

    List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id);

    List<Bill> selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId);
}
