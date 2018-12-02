package com.travelBill.api.transactions.core;

import com.travelBill.api.transactions.dataAccess.TransactionDataAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionDataAccessService transactionDataAccessService;

    @Autowired
    public TransactionService(TransactionDataAccessService transactionDataAccessService) {
        this.transactionDataAccessService = transactionDataAccessService;
    }

    public List<Transaction> getAll() {
        return transactionDataAccessService.getAll();
    }
}
