package com.travelBill.api.core.debt;

import com.travelBill.api.core.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DebtServiceTest {
    private DebtService debtService;
    private DebtRepository debtRepositorySpy = spy(DebtRepository.class);

    private Debt debt;
    private Event event;

    @BeforeEach
    void setupContext() {
        debtService = new DebtService(debtRepositorySpy);
        event = new Event();
        debt = Debt.newBuilder().withAmount(10)
                .withCurrency("USD")
                .build();
    }

    @Test
    void saveAll_shouldSaveDebts() {
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);

        debtService.saveAll(debts, event);
        verify(debtRepositorySpy).saveAll(debts);
    }

    @Test
    void saveAll_shouldSaveDebtsWithCommentsAccordingToEventTitle() {
        String eventTitle = "Eurotour";
        event.setTitle(eventTitle);
        List<Debt> debts = new ArrayList<>();
        debts.add(debt);

        doReturn(debts).when(debtRepositorySpy).saveAll(debts);
        debtService.saveAll(debts, event);

        assertEquals("Auto-calculated when event \"" + eventTitle + " \" was closed", debts.get(0).getComment());
    }
}