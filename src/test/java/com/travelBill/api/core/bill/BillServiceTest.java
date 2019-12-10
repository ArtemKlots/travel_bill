package com.travelBill.api.core.bill;

import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.exceptions.ClosedEventException;
import com.travelBill.api.core.exceptions.AccessDeniedException;
import com.travelBill.api.core.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BillServiceTest {
    private BillService billService;
    private BillRepository billRepository = spy(BillRepository.class);
    private static final String UAH = "UAH";
    private static final String USD = "USD";
    private List<Bill> bills;

    @BeforeEach
    void setupContext() {
        billService = new BillService(billRepository);
        bills = new ArrayList<>();
    }

    @Test
    void showTotalSpentByEvent_shouldReturnTotalSpentForOneBill() {
        Long eventId = 111L;

        Bill uahBill = new Bill();
        uahBill.setCurrency(UAH);
        uahBill.setAmount(1000);
        bills.add(uahBill);

        when(billRepository.findByEventId(eventId)).thenReturn(bills);
        List<CurrencyStatisticItem> result = billService.showTotalSpentByEvent(eventId);
        verify(billRepository).findByEventId(eventId);

        assertEquals(1, result.size());
        assertEquals(UAH, result.get(0).currency);
        assertEquals(1000, result.get(0).amount);
    }

    @Test
    void showTotalSpentByEvent_shouldReturnTotalSpentForTwoBillsInOneCurrency() {
        Long eventId = 111L;

        Bill uahBill1 = new Bill();
        uahBill1.setCurrency(UAH);
        uahBill1.setAmount(1000);
        Bill uahBill2 = new Bill();
        uahBill2.setCurrency(UAH);
        uahBill2.setAmount(1000);

        bills.add(uahBill1);
        bills.add(uahBill2);

        when(billRepository.findByEventId(eventId)).thenReturn(bills);
        List<CurrencyStatisticItem> result = billService.showTotalSpentByEvent(eventId);
        verify(billRepository).findByEventId(eventId);

        assertEquals(1, result.size());
        assertEquals(UAH, result.get(0).currency);
        assertEquals(2000, result.get(0).amount);
    }

    @Test
    void showTotalSpentByEvent_shouldReturnTotalSpentForTwoBillsInDifferentCurrency() {
        Long eventId = 111L;

        Bill uahBill = new Bill();
        uahBill.setCurrency(UAH);
        uahBill.setAmount(1000);
        Bill usdBill = new Bill();
        usdBill.setCurrency(USD);
        usdBill.setAmount(500);

        bills.add(uahBill);
        bills.add(usdBill);

        when(billRepository.findByEventId(eventId)).thenReturn(bills);
        List<CurrencyStatisticItem> result = billService.showTotalSpentByEvent(eventId);
        verify(billRepository).findByEventId(eventId);

        assertEquals(2, result.size());
        assertEquals(UAH, result.get(0).currency);
        assertEquals(1000, result.get(0).amount);

        assertEquals(USD, result.get(1).currency);
        assertEquals(500, result.get(1).amount);
    }

    @Test
    void showTotalSpentByEvent_shouldReturnTotalSpentForTwoBillsInDifferentCurrencyInDecreaseOrder() {
        Long eventId = 111L;

        Bill uahBill = new Bill();
        uahBill.setCurrency(UAH);
        uahBill.setAmount(500);
        Bill usdBill = new Bill();
        usdBill.setCurrency(USD);
        usdBill.setAmount(1000);

        bills.add(uahBill);
        bills.add(usdBill);

        when(billRepository.findByEventId(eventId)).thenReturn(bills);
        List<CurrencyStatisticItem> result = billService.showTotalSpentByEvent(eventId);
        verify(billRepository).findByEventId(eventId);

        assertEquals(2, result.size());
        assertEquals(USD, result.get(0).currency);
        assertEquals(1000, result.get(0).amount);

        assertEquals(UAH, result.get(1).currency);
        assertEquals(500, result.get(1).amount);
    }

    @Test
    void delete_shouldThrowAccessDeniedException_whenTryDeletBillByNotOwner() {
        User owner = new User();
        owner.setId(1L);
        User hacker = new User();
        hacker.setId(2L);

        Event event = new Event();
        event.setOpened(false);

        Bill bill = new Bill();
        bill.setEvent(event);
        bill.setUser(owner);

        assertThrows(AccessDeniedException.class, () -> billService.delete(bill, hacker));
    }

    @Test
    void delete_shouldThrowClosedEventException_whenTryToDeleteBillForClosedEvent() {
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        event.setOpened(false);

        Bill bill = new Bill();
        bill.setEvent(event);
        bill.setUser(user);

        assertThrows(ClosedEventException.class, () -> billService.delete(bill, user));
    }

    @Test
    void delete_shouldCallDeleteFromRepository_whenOwnerTryToDeleteBillFromOpenEvent() {
        User user = new User();
        user.setId(1L);
        Event event = new Event();
        event.setOpened(true);

        Bill bill = new Bill();
        bill.setEvent(event);
        bill.setUser(user);

        billService.delete(bill, user);
        verify(billRepository).deleteById(bill.getId());
    }

    @Test
    void save_shouldThrowClosedEventException_whenTryCreateBillForClosedEvent() {
        Event event = new Event();
        event.setOpened(false);

        Bill bill = new Bill();
        bill.setEvent(event);

        assertThrows(ClosedEventException.class, () -> billService.save(bill));
    }

    @Test
    void save_shouldCallSaveFromRepository_whenTryCreateBillForOpenedEvent() {
        Event event = new Event();
        event.setOpened(true);

        Bill bill = new Bill();
        bill.setEvent(event);

        billService.save(bill);
        verify(billRepository).save(bill);
    }
}