package com.travelBill.api.core.bill;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.bill.debtCalculator.MultiCurrencyDebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MultiCurrencyDebtCalculatorTest {

    private final EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    private MultiCurrencyDebtCalculator multiCurrencyDebtCalculator;
    private Event event;
    private List<Bill> bills;
    private List<Debt> expectedDebts;

    private User john, jane, judy, james, janie;
    private Bill johnsDollarBill, janesDollarBill, judysDollarBill, jamesDollarBill;
    private Bill johnsEuroBill, janesEuroBill, judysEuroBill, jamesEuroBill;

    @Spy @InjectMocks private DebtCalculator spyDebtCalculator;

    @BeforeEach
    void setupContext() {
        multiCurrencyDebtCalculator = new MultiCurrencyDebtCalculator(spyDebtCalculator);
        event = random.nextObject(Event.class);
        bills = new ArrayList<>();
        event.setBills(bills);
        expectedDebts = new ArrayList<>();
    }

    @BeforeEach
    void setupUsersAndBills() {
        john = random.nextObject(User.class);
        john.setFirstName("John");

        jane = random.nextObject(User.class);
        jane.setFirstName("Jane");

        judy = random.nextObject(User.class);
        judy.setFirstName("Judy");

        james = random.nextObject(User.class);
        james.setFirstName("James");

        janie = random.nextObject(User.class);
        janie.setFirstName("Janie");

        johnsDollarBill = new Bill();
        johnsDollarBill.setUser(john);
        johnsDollarBill.setCurrency("USD");

        janesDollarBill = new Bill();
        janesDollarBill.setUser(jane);
        janesDollarBill.setCurrency("USD");

        judysDollarBill = new Bill();
        judysDollarBill.setUser(judy);
        judysDollarBill.setCurrency("USD");

        jamesDollarBill = new Bill();
        jamesDollarBill.setUser(james);
        jamesDollarBill.setCurrency("USD");


        johnsEuroBill = new Bill();
        johnsEuroBill.setUser(john);
        johnsEuroBill.setCurrency("EUR");

        janesEuroBill = new Bill();
        janesEuroBill.setUser(jane);
        janesEuroBill.setCurrency("EUR");

        judysEuroBill = new Bill();
        judysEuroBill.setUser(judy);
        judysEuroBill.setCurrency("EUR");

        jamesEuroBill = new Bill();
        jamesEuroBill.setUser(james);
        jamesEuroBill.setCurrency("EUR");
    }

    @Test
    void calculate_shouldCallDebtCalculator() {
        johnsDollarBill.setAmount(20);
        janesDollarBill.setAmount(5);
        judysDollarBill.setAmount(5);

        johnsEuroBill.setAmount(20);
        janesEuroBill.setAmount(5);
        judysEuroBill.setAmount(5);

        bills.addAll(Arrays.asList(johnsDollarBill, janesDollarBill, judysDollarBill, johnsEuroBill, janesEuroBill, judysEuroBill));
        event.setMembers(Arrays.asList(john, jane, judy));


        doReturn(new ArrayList<>()).when(spyDebtCalculator).calculate(any(), any());

        spyDebtCalculator.calculate(new ArrayList<>(), new ArrayList<>());
        List<Debt> expectedDebts = new ArrayList<>();

        List<Debt> actualDebts = multiCurrencyDebtCalculator.calculate(new Event());

        assertEquals(expectedDebts, actualDebts);

    }
}
