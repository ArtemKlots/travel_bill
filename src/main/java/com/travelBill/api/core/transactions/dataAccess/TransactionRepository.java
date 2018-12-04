package com.travelBill.api.core.transactions.dataAccess;

import com.travelBill.api.core.transactions.core.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
