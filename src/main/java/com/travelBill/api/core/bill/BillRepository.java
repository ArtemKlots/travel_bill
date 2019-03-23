package com.travelBill.api.core.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id);
}
