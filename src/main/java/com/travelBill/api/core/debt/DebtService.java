package com.travelBill.api.core.debt;

import com.travelBill.api.core.event.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DebtService {

    private final DebtRepository debtRepository;

    @Autowired
    public DebtService(DebtRepository debtRepository) {
        this.debtRepository = debtRepository;
    }

    public Debt add(Debt debt) {
        return debtRepository.save(debt);
    }

    public List<DebtSumDto> getBalanceForUser(Long userId) {
        return debtRepository.getDebtsForUser(userId);
    }

    public List<Debt> getHistoryForUser(Long userId) {
        return debtRepository.getHistoryForUser(userId);
    }

    public Debt save(Debt debt) {
        return debtRepository.save(debt);
    }

    public List<Debt> saveAll(List<Debt> debts, Event event) {
        debts.forEach(debt -> debt.setComment(String.format("Auto-calculated when event \"%s \" was closed", event.getTitle())));
        return debtRepository.saveAll(debts);
    }
}
