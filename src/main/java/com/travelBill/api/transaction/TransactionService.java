package com.travelBill.api.transaction;

import com.travelBill.api.core.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository TransactionRepository) {
        this.transactionRepository = TransactionRepository;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }
}
