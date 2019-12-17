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

    public Debt save(Debt debt) {
        return debtRepository.save(debt);
    }

    public List<Debt> saveAll(List<Debt> debts, Event event) {
        debts.forEach(debt -> debt.setComment(String.format("Payment from event %s", event.getTitle())));
        return debtRepository.saveAll(debts);
    }
}
