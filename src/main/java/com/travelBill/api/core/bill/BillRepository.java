package com.travelBill.api.core.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findTop10ByUserIdOrderByCreatedAtDesc(Long id);

    List<Bill> findTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId);
}
