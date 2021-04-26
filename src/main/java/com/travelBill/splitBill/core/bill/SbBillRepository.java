package com.travelBill.splitBill.core.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SbBillRepository extends JpaRepository<SbBill, Long> {
    List<SbBill> getSbBillsByOwnerId(Long ownerId);
    List<SbBill> getSbBillsByMembersIdOrderByCreatedAtDesc(Long ownerId);
}
