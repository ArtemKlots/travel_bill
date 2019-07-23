package com.travelBill.api.core.bill;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillServiceImpl(BillRepository BillRepository) {
        this.billRepository = BillRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    @Override
    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public void deleteById(Long id) {
        billRepository.deleteById(id);
    }

    public List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id) {
        return billRepository.findTop10ByUserIdOrderByCreatedAtDesc(id);
    }

    public List<Bill> selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId) {
        return billRepository.findTop10ByUserIdAndEventIdOrderByCreatedAtDesc(userId, eventId);
    }
}
