package com.travelBill.api.transactions.dataAccess;

import com.travelBill.api.transactions.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
