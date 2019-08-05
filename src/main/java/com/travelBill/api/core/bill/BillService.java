package com.travelBill.api.core.bill;

import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository BillRepository) {
        this.billRepository = BillRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    public Bill findById(Long id) {
        return billRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Bill save(Bill bill) {
        return billRepository.save(bill);
    }

    public void delete(Bill bill, User user) {
        if (bill.getUser().getId().equals(user.getId())) {
            billRepository.deleteById(bill.getId());
        } else {
            throw new AccessDeniedException("You've tried to access not your bill");
        }
    }

    public List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id) {
        return billRepository.findTop10ByUserIdOrderByCreatedAtDesc(id);
    }

    public List<Bill> selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId) {
        return billRepository.findTop10ByUserIdAndEventIdOrderByCreatedAtDesc(userId, eventId);
    }
}
