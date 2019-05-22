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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MultiCurrencyDebtCalculatorTest {

    private final EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    private DebtCalculator mockDebtCalculator;
    private MultiCurrencyDebtCalculator multiCurrencyDebtCalculator;
    private Event event;
    private List<Bill> bills;
    private List<Debt> expectedDebts;

    private User john, jane, judy, james, janie;
    private Bill johnsDollarBill, janesDollarBill, judysDollarBill, jamesDollarBill;
    private Bill johnsEuroBill, janesEuroBill, judysEuroBill, jamesEuroBill;



    @BeforeEach
    void setupContext() {
        mockDebtCalculator = Mockito.mock(DebtCalculator.class);
        multiCurrencyDebtCalculator = new MultiCurrencyDebtCalculator(mockDebtCalculator);
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
    void calculate_shouldCallDebtCalculator_forOnePerson() {
        Event event = new Event();
        bills.addAll(Arrays.asList(johnsDollarBill, johnsEuroBill));
        event.setMembers(Arrays.asList(john));
        event.setBills(bills);

        Debt dollarDebt = Debt.newBuilder().withDebtor(john).withPayer(john).withCurrency("USD").build();
        Debt euroDebt = Debt.newBuilder().withDebtor(john).withPayer(john).withCurrency("EUR").build();

        doReturn(Arrays.asList(dollarDebt)).when(mockDebtCalculator).calculate(Collections.singletonList(johnsDollarBill), event.getMembers());
        doReturn(Arrays.asList(euroDebt)).when(mockDebtCalculator).calculate(Collections.singletonList(johnsEuroBill), event.getMembers());


        List<Debt> actualResult = multiCurrencyDebtCalculator.calculate(event);
        Mockito.verify(mockDebtCalculator, times(1)).calculate(Collections.singletonList(johnsDollarBill), event.getMembers());
        Mockito.verify(mockDebtCalculator, times(1)).calculate(Collections.singletonList(johnsEuroBill), event.getMembers());

        assertEquals(Arrays.asList(euroDebt, dollarDebt), actualResult);
    }

    @Test
    void calculate_shouldRequestDebtCalculator_forTwoPersons() {
        Event event = new Event();
        bills.addAll(Arrays.asList(johnsDollarBill, janesEuroBill, johnsEuroBill));
        event.setMembers(Arrays.asList(john));
        event.setBills(bills);


        Debt dollarDebt = Debt.newBuilder().withDebtor(john).withPayer(john).withCurrency("USD").build();
        Debt euroDebt = Debt.newBuilder().withDebtor(john).withPayer(john).withCurrency("EUR").build();

        doReturn(Arrays.asList(dollarDebt)).when(mockDebtCalculator).calculate(Arrays.asList(johnsDollarBill), event.getMembers());
        //TODO find a way to avoid order
        doReturn(Arrays.asList(euroDebt)).when(mockDebtCalculator).calculate(Arrays.asList(janesEuroBill, johnsEuroBill), event.getMembers());


        List<Debt> actualResult = multiCurrencyDebtCalculator.calculate(event);
        Mockito.verify(mockDebtCalculator, times(1)).calculate(Arrays.asList(johnsDollarBill), event.getMembers());
        Mockito.verify(mockDebtCalculator, times(1)).calculate(Arrays.asList(janesEuroBill, johnsEuroBill), event.getMembers());

        //TODO find a way to avoid order
        assertEquals(Arrays.asList(euroDebt, dollarDebt), actualResult);
    }
}
