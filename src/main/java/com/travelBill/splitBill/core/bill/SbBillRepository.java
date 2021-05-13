package com.travelBill.splitBill.core.bill;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SbBillRepository extends JpaRepository<SbBill, Long> {
    List<SbBill> getSbBillsByOwnerId(Long ownerId);
    List<SbBill> getSbBillsByMembersIdOrderByCreatedAtDesc(Long ownerId);
    Optional<SbBill> getSbBillByInviteId(String inviteId);
}
