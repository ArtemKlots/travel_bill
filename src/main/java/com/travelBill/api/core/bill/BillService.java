package com.travelBill.api.core.bill;

import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import com.travelBill.api.core.bill.statistic.UserSpentStatisticItem;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * @param bill
     * @return new bill
     * @throws ClosedEventException when event is closed
     */
    public Bill save(Bill bill) {
        if (!bill.getEvent().isOpened()) {
            throw new ClosedEventException();
        }

        return billRepository.save(bill);
    }

    /**
     * @param bill to delete
     * @param user who deletes
     * @throws AccessDeniedException when provided user is not owner
     * @throws ClosedEventException  when event is closed
     */
    public void delete(Bill bill, User user) {
        if (!bill.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException("You've tried to access not your bill");
        }

        if (!bill.getEvent().isOpened()) {
            throw new ClosedEventException();
        }

        billRepository.deleteById(bill.getId());
    }

    public List<Bill> selectTop10ByUserIdOrderByCreatedAtDesc(Long id) {
        return billRepository.findTop10ByUserIdOrderByCreatedAtDesc(id);
    }

    public List<Bill> selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(Long userId, Long eventId) {
        return billRepository.findTop10ByUserIdAndEventIdOrderByCreatedAtDesc(userId, eventId);
    }


    public List<CurrencyStatisticItem> showTotalSpentByEvent(Long eventId) {
        List<Bill> bills = billRepository.findByEventId(eventId);
        return this.calculateStatisticForBills(bills);
    }

    public List<UserSpentStatisticItem> showSpentByPersonByEvent(Long eventId) {
        List<Bill> bills = billRepository.findByEventId(eventId);
        List<User> users = bills.stream().map(Bill::getUser).collect(Collectors.toList());

        return users.stream()
                .map(user -> {
                    List<Bill> billsForUser = bills.stream().filter(bill -> bill.getUser().getId().equals(user.getId())).collect(Collectors.toList());
                    List<CurrencyStatisticItem> userStatistic = this.calculateStatisticForBills(billsForUser);

                    return new UserSpentStatisticItem(user, userStatistic);
                })
                .collect(Collectors.toList());
    }

    private List<CurrencyStatisticItem> calculateStatisticForBills(List<Bill> bills) {
        List<String> currencies = bills.stream().map(Bill::getCurrency).distinct().collect(Collectors.toList());
        return currencies.stream()
                .map(currency -> {
                    List<Bill> billsForCurrency = bills.stream().filter(bill -> bill.getCurrency().equals(currency)).collect(Collectors.toList());
                    Double billsAmount = billsForCurrency.stream().mapToDouble(Bill::getAmount).sum();
                    return new CurrencyStatisticItem(currency, billsAmount);
                })
                .sorted(Comparator.comparingDouble(CurrencyStatisticItem::getAmount).reversed())
                .collect(Collectors.toList());
    }
}
