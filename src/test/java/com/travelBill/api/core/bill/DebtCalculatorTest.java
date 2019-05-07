package com.travelBill.api.core.bill;

import com.travelBill.api.core.bill.debtCalculator.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebtCalculatorTest {

    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    private User john;
    private User jane;
    private User judy;
    private User james;
    private User janie;

    private Bill johnsBill;
    private Bill janesBill;
    private Bill judysBill;
    private Bill jamesBill;

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

        johnsBill = new Bill();
        johnsBill.setUser(john);

        janesBill = new Bill();
        janesBill.setUser(jane);

        judysBill = new Bill();
        judysBill.setUser(judy);

        jamesBill = new Bill();
        jamesBill.setUser(james);
    }

    @Test
    void calculate_shouldReturn2DebtsWithAmount5_for2DebtorsAnd1Payer() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(20);
        bills.add(johnsBill);

        janesBill.setAmount(5);
        bills.add(janesBill);

        judysBill.setAmount(5);
        bills.add(judysBill);

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane, judy));

        List<Debt> expectedDebts = new ArrayList<>();

        Debt janesDebt = random.nextObject(Debt.class);
        janesDebt.amount = 5;
        janesDebt.debtor = jane;
        janesDebt.payer = john;
        expectedDebts.add(janesDebt);

        Debt judysDebt = random.nextObject(Debt.class);
        judysDebt.amount = 5;
        judysDebt.debtor = judy;
        judysDebt.payer = john;
        expectedDebts.add(judysDebt);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturnTwoDebts_forOneDebtorAndTwoPayers() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(25);
        bills.add(johnsBill);

        janesBill.setAmount(25);
        bills.add(janesBill);

        judysBill.setAmount(10);
        bills.add(judysBill);

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane, judy));

        List<Debt> expectedDebts = new ArrayList<>();

        Debt judysDebtForJohn = random.nextObject(Debt.class);
        judysDebtForJohn.amount = 5;
        judysDebtForJohn.debtor = judy;
        judysDebtForJohn.payer = john;
        expectedDebts.add(judysDebtForJohn);

        Debt judysDebtForJane = random.nextObject(Debt.class);
        judysDebtForJane.amount = 5;
        judysDebtForJane.debtor = judy;
        judysDebtForJane.payer = jane;
        expectedDebts.add(judysDebtForJane);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturnDebtFor1Debtor_WhenPayerPaidBillsAndDebtorDidnt() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(100);
        bills.add(johnsBill);

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane));

        List<Debt> expectedDebts = new ArrayList<>();

        Debt debt = random.nextObject(Debt.class);
        debt.amount = 50;
        debt.debtor = jane;
        debt.payer = john;
        expectedDebts.add(debt);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn4DebtsForOneDebtor_When5Payers() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(100);
        bills.add(johnsBill);

        janesBill.setAmount(100);
        bills.add(janesBill);

        judysBill.setAmount(100);
        bills.add(judysBill);

        jamesBill.setAmount(100);
        bills.add(jamesBill);

        //janie paid nothing

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane, judy, james, janie));

        List<Debt> expectedDebts = new ArrayList<>();

        Arrays.asList(john, jane, judy, james).forEach(user -> {
            Debt debt = random.nextObject(Debt.class);
            debt.amount = 20;
            debt.debtor = janie;
            debt.payer = user;
            expectedDebts.add(debt);
        });

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn3DebtsFor2DebtorAnd3Payers_WhenPayersPaid120And100And20() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(120);
        bills.add(johnsBill);

        janesBill.setAmount(100);
        bills.add(janesBill);

        judysBill.setAmount(20);
        bills.add(judysBill);

        //james paid nothing

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane, judy, james));

        List<Debt> expectedDebts = new ArrayList<>();

        Debt judysDebt = random.nextObject(Debt.class);
        judysDebt.amount = 40;
        judysDebt.debtor = judy;
        judysDebt.payer = john;
        expectedDebts.add(judysDebt);

        Debt jamesDebtToJohn = random.nextObject(Debt.class);
        jamesDebtToJohn.amount = 20;
        jamesDebtToJohn.debtor = james;
        jamesDebtToJohn.payer = john;
        expectedDebts.add(jamesDebtToJohn);

        Debt jamesDebtToJane = random.nextObject(Debt.class);
        jamesDebtToJane.amount = 40;
        jamesDebtToJane.debtor = james;
        jamesDebtToJane.payer = jane;
        expectedDebts.add(jamesDebtToJane);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn2DebtsFor2DebtorsAnd3Payers_WhenFirstDebtorShouldPayDebtForFirstPayerOnly() {
        DebtCalculator calculator = new DebtCalculator();

        List<Bill> bills = new ArrayList<>();
        johnsBill.setAmount(120);
        bills.add(johnsBill);

        janesBill.setAmount(100);
        bills.add(janesBill);

        judysBill.setAmount(20);
        bills.add(judysBill);

        //james paid nothing

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(james, john, jane, judy));

        List<Debt> expectedDebts = new ArrayList<>();

        Debt jamesDebtToJohn = random.nextObject(Debt.class);
        jamesDebtToJohn.amount = 60;
        jamesDebtToJohn.debtor = james;
        jamesDebtToJohn.payer = john;
        expectedDebts.add(jamesDebtToJohn);

        Debt judysDebtToJane = random.nextObject(Debt.class);
        judysDebtToJane.amount = 40;
        judysDebtToJane.debtor = judy;
        judysDebtToJane.payer = jane;
        expectedDebts.add(judysDebtToJane);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }
}
