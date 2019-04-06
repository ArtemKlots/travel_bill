package com.travelBill.api.core.bill;

import com.travelBill.api.core.Debt;
import com.travelBill.api.core.bill.debtCalculator.DebtCalculator;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebtCalculatorTest {

    public static final String USD = "USD";
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();

    @Test
    public void calculate_shouldReturnDebts_forEvent() {
        DebtCalculator calculator = new DebtCalculator();

        User john = random.nextObject(User.class);
        john.setFirstName("John");
        User jane = random.nextObject(User.class);
        jane.setFirstName("Jane");

        List<Bill> bills = new ArrayList<>();
        Bill johnsBill = new Bill();
        johnsBill.setUser(john);
        johnsBill.setAmount(10);
        johnsBill.setCurrency(USD);
        bills.add(johnsBill);

        Bill janesBill = new Bill();
        janesBill.setUser(jane);
        janesBill.setAmount(5);
        janesBill.setCurrency(USD);
        bills.add(janesBill);

        Event event = random.nextObject(Event.class);
        event.setBills(bills);
        event.setMembers(Arrays.asList(john, jane));

        List<Debt> expectedDebts = new ArrayList<>();
        Debt janesDebt = random.nextObject(Debt.class);
        janesDebt.amount = 2.5;
        expectedDebts.add(janesDebt);

        List<Debt> actualDebts = calculator.calculate(event);

        assertEquals(expectedDebts.size(), actualDebts.size());
    }
}
