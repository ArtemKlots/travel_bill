package com.travelBill.api.core.bill;

import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
}