package com.travelBill.api.transaction;

import com.travelBill.api.core.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
