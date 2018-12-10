package com.travelBill.api.bill;

import com.travelBill.api.core.Bill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
}
