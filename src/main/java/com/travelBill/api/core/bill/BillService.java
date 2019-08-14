package com.travelBill.api.core.bill;

import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional
public class BillService {

    private final BillRepository billRepository;

    @Autowired
    public BillService(BillRepository BillRepository) {
        this.billRepository = BillRepository;
    }

    public List<Bill> getAll() {
        return billRepository.findAll();
    }

    /**
     * Retrieves a bill by its id.
     *
     * @param billId must not be null.
     * @param userId id of user who requested a bill. Must not be null.
     * @return requested Bill
     * @throws AccessDeniedException if requested user is not owner of the bill
     */
    public Bill findById(@NotNull Long billId, @NotNull Long userId) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(EntityNotFoundException::new);

        if (!bill.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not the owner of the bill");
        }

        return bill;
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
