package com.travelBill.api.core.debt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DebtService {

    private final DebtRepository debtRepository;

    @Autowired
    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Debt save(Debt debt) {
        return debtRepository.save(debt);
    }
}
