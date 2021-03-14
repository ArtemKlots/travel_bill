package com.travelBill.splitBill.core;

import com.travelBill.api.core.debt.Debt;
import com.travelBill.api.core.user.User;
import com.travelBill.splitBill.core.assigning.Assign;
import com.travelBill.splitBill.core.bill.SbBill;
import com.travelBill.splitBill.core.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebtCalculatorTest {
    private User owner, user2;
    private SbBill bill;
    private Item item1;

    @BeforeEach
    void prepareUsers() {
        owner = new User();
        user2 = new User();

        owner.setId(1L);
        user2.setId(2L);
    }

    @BeforeEach
    void prepareBills() {
        bill = new SbBill();
        bill.setCurrency("uah");
    }

    @BeforeEach
    void prepareItems() {
        item1 = new Item();
    }

    @Test
    void shouldCalculateDebt_ForOneItem_OneAmount_OneUser() {
        Assign user1Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(1);
        item1.setPrice(10);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        item1.setAssigns(Collections.singletonList(user1Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(1, debts.size());
        assertEquals(10, debts.get(0).getAmount());
        assertEquals(bill.getTitle(), debts.get(0).getComment());
        assertEquals(bill.getCurrency(), debts.get(0).getCurrency());
        assertEquals(bill.getOwner(), debts.get(0).getPayer());
        assertEquals(owner, debts.get(0).getDebtor());
    }

    @Test
    void shouldCalculateDebt_ForOneItem_OneAmount_TwoUsers_AndEqualsParts() {
        Assign user1Assign = new Assign();
        Assign user2Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(1);
        item1.setPrice(10);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        user2Assign.setUser(user2);
        user2Assign.setAmount(1);

        item1.setAssigns(Arrays.asList(user1Assign, user2Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(debts.size(), 2);
        assertEquals(debts.get(0).getAmount(), 5);
        assertEquals(debts.get(0).getComment(), bill.getTitle());
        assertEquals(debts.get(0).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(0).getPayer(), bill.getOwner());
        assertEquals(debts.get(0).getDebtor(), owner);

        assertEquals(debts.get(1).getAmount(), 5);
        assertEquals(debts.get(1).getComment(), bill.getTitle());
        assertEquals(debts.get(1).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(1).getPayer(), bill.getOwner());
        assertEquals(debts.get(1).getDebtor(), user2);
    }

    @Test
    void shouldCalculateDebt_ForOneItem_OneAmount_TwoUsers_AndNotEqualsParts() {
        Assign user1Assign = new Assign();
        Assign user2Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(1);
        item1.setPrice(15);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        user2Assign.setUser(user2);
        user2Assign.setAmount(2);

        item1.setAssigns(Arrays.asList(user1Assign, user2Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(debts.size(), 2);
        assertEquals(debts.get(0).getAmount(), 5);
        assertEquals(debts.get(0).getComment(), bill.getTitle());
        assertEquals(debts.get(0).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(0).getPayer(), bill.getOwner());
        assertEquals(debts.get(0).getDebtor(), owner);

        assertEquals(debts.get(1).getAmount(), 10);
        assertEquals(debts.get(1).getComment(), bill.getTitle());
        assertEquals(debts.get(1).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(1).getPayer(), bill.getOwner());
        assertEquals(debts.get(1).getDebtor(), user2);
    }

    @Test
    void shouldCalculateDebt_ForOneItem_TwoAmounts_TwoUsers_AndEqualsParts() {
        Assign user1Assign = new Assign();
        Assign user2Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(2);
        item1.setPrice(10);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        user2Assign.setUser(user2);
        user2Assign.setAmount(1);

        item1.setAssigns(Arrays.asList(user1Assign, user2Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(debts.size(), 2);
        assertEquals(10, debts.get(0).getAmount());
        assertEquals(debts.get(0).getComment(), bill.getTitle());
        assertEquals(debts.get(0).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(0).getPayer(), bill.getOwner());
        assertEquals(debts.get(0).getDebtor(), owner);

        assertEquals(debts.get(1).getAmount(), 10);
        assertEquals(debts.get(1).getComment(), bill.getTitle());
        assertEquals(debts.get(1).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(1).getPayer(), bill.getOwner());
        assertEquals(debts.get(1).getDebtor(), user2);
    }

    @Test
    void shouldCalculateDebt_ForOneItem_TwoAmounts_TwoUsers_AndNotEqualsParts() {
        Assign user1Assign = new Assign();
        Assign user2Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(2);
        item1.setPrice(9);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        user2Assign.setUser(user2);
        user2Assign.setAmount(2);

        item1.setAssigns(Arrays.asList(user1Assign, user2Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(debts.size(), 2);
        assertEquals(6, debts.get(0).getAmount());
        assertEquals(debts.get(0).getComment(), bill.getTitle());
        assertEquals(debts.get(0).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(0).getPayer(), bill.getOwner());
        assertEquals(debts.get(0).getDebtor(), owner);

        assertEquals(12, debts.get(1).getAmount());
        assertEquals(debts.get(1).getComment(), bill.getTitle());
        assertEquals(debts.get(1).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(1).getPayer(), bill.getOwner());
        assertEquals(debts.get(1).getDebtor(), user2);
    }

    @Test
    void shouldCalculateDebt_ForOneItem_ThreeAmounts_TwoUsers_AndNotAssignedParts() {
        Assign user1Assign = new Assign();
        Assign user2Assign = new Assign();

        bill.setItems(Collections.singletonList(item1));
        bill.setMembers(Arrays.asList(owner, user2));
        bill.setOwner(owner);
        bill.setTitle("Test");

        item1.setAmount(3);
        item1.setPrice(10);
        item1.setTitle("Test");

        user1Assign.setUser(owner);
        user1Assign.setAmount(1);

        user2Assign.setUser(user2);
        user2Assign.setAmount(1);

        item1.setAssigns(Arrays.asList(user1Assign, user2Assign));

        List<Debt> debts = new DebtCalculator().calculate(bill);
        assertEquals(debts.size(), 2);
        assertEquals(15, debts.get(0).getAmount());
        assertEquals(debts.get(0).getComment(), bill.getTitle());
        assertEquals(debts.get(0).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(0).getPayer(), bill.getOwner());
        assertEquals(debts.get(0).getDebtor(), owner);

        assertEquals(15, debts.get(1).getAmount());
        assertEquals(debts.get(1).getComment(), bill.getTitle());
        assertEquals(debts.get(1).getCurrency(), bill.getCurrency());
        assertEquals(debts.get(1).getPayer(), bill.getOwner());
        assertEquals(debts.get(1).getDebtor(), user2);
    }
}
