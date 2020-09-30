package com.travelBill.api.core.bill;


import com.travelBill.api.core.debt.debtCalculator.DeprecatedDebtCalculator;
import com.travelBill.api.core.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DeprecatedDebtCalculatorTest {
    private static DecimalFormat decimalFormat = new DecimalFormat(".##");
    private DeprecatedDebtCalculator debtCalculator = new DeprecatedDebtCalculator();
    private User user1, user2, user3;
    private Bill bill1, bill2, bill3;

    @BeforeEach
    void prepareUsers() {
        user1 = new User();
        user2 = new User();
        user3 = new User();

        user1.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
    }

    @BeforeEach
    void prepareBills() {
        bill1 = new Bill();
        bill2 = new Bill();
        bill3 = new Bill();
    }

    @Test
    void shouldResolveDebtsBetweenThreePersonWithOneBillPerPerson() {
        List<User> users = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        bill1.setUser(user1);
        bill1.setAmount(100);

        bill2.setUser(user2);
        bill2.setAmount(150);

        bill3.setUser(user3);
        bill3.setAmount(10);

        users.add(user1);
        users.add(user2);
        users.add(user3);

        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);


        Map<User, Map<User, Double>> result = debtCalculator.getDebts(users, bills);

        assertEquals(result.size(), 1);
        assertNotNull(result.get(user3));
        assertEquals(result.get(user3).size(), 2);

        String roundedValue1 = decimalFormat.format(result.get(user3).get(user1));
        String roundedValue2 = decimalFormat.format(result.get(user3).get(user2));
        assertEquals(roundedValue1, "13.33");
        assertEquals(roundedValue2, "63.33");
    }

    @Test
    void shouldResolveDebtsBetweenOnePayerAndOneDebtorWithSeveralBills() {
        List<User> users = new ArrayList<>();
        List<Bill> bills = new ArrayList<>();

        bill1.setAmount(1000);
        bill1.setUser(user1);

        bill2.setAmount(737.5);
        bill2.setUser(user1);

        bill3.setUser(user2);
        bill3.setAmount(15);

        users.add(user1);
        users.add(user2);

        bills.add(bill1);
        bills.add(bill2);
        bills.add(bill3);


        Map<User, Map<User, Double>> result = debtCalculator.getDebts(users, bills);

        assertEquals(result.size(), 1);
        assertNotNull(result.get(user2));
        assertEquals(result.get(user2).size(), 1);

        String roundedValue = decimalFormat.format(result.get(user2).get(user1));
        assertEquals(roundedValue, "861.25");
    }
}
