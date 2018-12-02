package com.travelBill.api.transactions.dataAccess;

import com.travelBill.api.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDataAccessService {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionDataAccessService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }
}
