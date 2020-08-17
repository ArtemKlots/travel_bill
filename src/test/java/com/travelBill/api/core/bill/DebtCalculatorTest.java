package com.travelBill.api.core.bill;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.debt.debtCalculator.DebtCalculator;
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

    private final EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();
    private DebtCalculator calculator;
    private Event event;
    private List<Bill> bills;
    private List<Debt> expectedDebts;

    private User john, jane, judy, james, janie;
    private Bill johnsBill, janesBill, judysBill, jamesBill;
    private static String USD = "USD";
    private static String EUR = "EUR";

    @BeforeEach
    void setupContext() {
        calculator = new DebtCalculator();
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

        johnsBill = Bill.newBuilder().withUser(john).withCurrency(USD).build();
        johnsBill.setUser(john);
        johnsBill.setCurrency(USD);

        janesBill = new Bill();
        janesBill.setUser(jane);
        janesBill.setCurrency(USD);

        judysBill = new Bill();
        judysBill.setUser(judy);
        judysBill.setCurrency(USD);

        jamesBill = new Bill();
        jamesBill.setUser(james);
        jamesBill.setCurrency(USD);
    }

    @Test
    void calculate_shouldReturn2DebtsWithAmount5_for2DebtorsAnd1Payer() {
        johnsBill.setAmount(20);
        janesBill.setAmount(5);
        judysBill.setAmount(5);

        bills.addAll(Arrays.asList(johnsBill, janesBill, judysBill));
        event.setMembers(Arrays.asList(john, jane, judy));

        List<Debt> expectedDebts = new ArrayList<>();

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(jane)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build());

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build());

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturnTwoDebts_forPersonWhoPaidLess() {
        johnsBill.setAmount(25);
        janesBill.setAmount(25);
        judysBill.setAmount(10);

        bills.addAll(Arrays.asList(johnsBill, janesBill, judysBill));
        event.setMembers(Arrays.asList(john, jane, judy));

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build());

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(jane)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build());

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturnDebtFor1Debtor_WhenPayerPaidBillsAndDebtorDidnt() {
        johnsBill.setAmount(100);
        bills.add(johnsBill);

        event.setMembers(Arrays.asList(john, jane));

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(jane)
                        .withPayer(john)
                        .withAmount(50)
                        .withCurrency(USD)
                        .build());

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn4DebtsForOneDebtor_When5PayersPaidSameAmount() {
        johnsBill.setAmount(100);
        janesBill.setAmount(100);
        judysBill.setAmount(100);
        jamesBill.setAmount(100);

        bills.addAll(Arrays.asList(johnsBill, janesBill, judysBill, jamesBill));
        //janie paid nothing
        event.setMembers(Arrays.asList(john, jane, judy, james, janie));

        Arrays.asList(john, jane, judy, james).forEach(user -> expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(janie)
                        .withPayer(user)
                        .withAmount(20)
                        .withCurrency(USD)
                        .build()
        ));

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn3Debts_WhenOnePaidNothing() {
        johnsBill.setAmount(120);
        janesBill.setAmount(100);
        judysBill.setAmount(20);

        bills.addAll(Arrays.asList(johnsBill, janesBill, judysBill));
        //james paid nothing
        event.setMembers(Arrays.asList(john, jane, judy, james));

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(john)
                        .withAmount(40)
                        .withCurrency(USD)
                        .build());

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(james)
                        .withPayer(john)
                        .withAmount(20)
                        .withCurrency(USD)
                        .build());

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(james)
                        .withPayer(jane)
                        .withAmount(40)
                        .withCurrency(USD)
                        .build());

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturn2DebtsFor2DebtorsAnd3Payers_WhenFirstDebtorShouldPayDebtForFirstPayerOnly() {
        johnsBill.setAmount(120);
        janesBill.setAmount(100);
        judysBill.setAmount(20);

        bills.addAll(Arrays.asList(johnsBill, janesBill, judysBill));
        //james paid nothing

        event.setMembers(Arrays.asList(james, john, jane, judy));

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(james)
                        .withPayer(john)
                        .withAmount(60)
                        .withCurrency(USD)
                        .build());

        expectedDebts.add(
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(jane)
                        .withAmount(40)
                        .withCurrency(USD)
                        .build());

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }

    @Test
    void calculate_shouldReturnDebtsInTwoCurrencies_WhenPeoplePaidInTwoCurrencies() {
        bills.addAll(Arrays.asList(
                Bill.newBuilder().withUser(john).withAmount(20).withCurrency(USD).build(),
                Bill.newBuilder().withUser(jane).withAmount(5).withCurrency(USD).build(),
                Bill.newBuilder().withUser(judy).withAmount(5).withCurrency(USD).build(),
                Bill.newBuilder().withUser(john).withAmount(20).withCurrency(EUR).build(),
                Bill.newBuilder().withUser(jane).withAmount(5).withCurrency(EUR).build(),
                Bill.newBuilder().withUser(judy).withAmount(5).withCurrency(EUR).build()
        ));

        event.setMembers(Arrays.asList(john, jane, judy));

        expectedDebts.addAll(Arrays.asList(
                Debt.newBuilder()
                        .withDebtor(jane)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(EUR)
                        .build(),
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(EUR)
                        .build(),
                Debt.newBuilder()
                        .withDebtor(jane)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build(),
                Debt.newBuilder()
                        .withDebtor(judy)
                        .withPayer(john)
                        .withAmount(5)
                        .withCurrency(USD)
                        .build()
        ));


        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts, actualDebts);
    }
}
