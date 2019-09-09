package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.formatter.bill.LastBillsListFormatter;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LastBillsListFormatterTest {
    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();

    @Test
    void lastTransactionsListFormatter_shouldReturnFormattedBillWithDescription_forOneBill() {
        Bill bill = random.nextObject(Bill.class);
        bill.setAmount(10.50);
        bill.setCurrency("$");
        bill.setPurpose("for sandwiches");

        String expectedResult = "Here are your last bills:\n10.50 $ for sandwiches\n";
        String actualResult = new LastBillsListFormatter().format(Collections.singletonList(bill));

        assertEquals(expectedResult, actualResult);
        //Можно так делать?
        assertTrue(actualResult.startsWith("Here are your last bills:\n"), "expect text to be started from description");
        assertEquals(expectedResult, actualResult, "expect price to be formatted as 10.50");
    }

    @Test
    void lastTransactionsListFormatter_shouldReturnFormattedBillList_forSeveralBills() {
        Bill billFoo = random.nextObject(Bill.class);
        billFoo.setAmount(5);
        billFoo.setCurrency("$");
        billFoo.setPurpose("toll road");

        Bill billBar = random.nextObject(Bill.class);
        billBar.setAmount(10);
        billBar.setCurrency("€");
        billBar.setPurpose("sandwiches");

        Bill billBaz = random.nextObject(Bill.class);
        billBaz.setAmount(15);
        billBaz.setCurrency("£");
        billBaz.setPurpose("ticket");

        String expectedResult = "Here are your last bills:\n" +
                "5.00 $ toll road\n" +
                "10.00 € sandwiches\n" +
                "15.00 £ ticket\n";
        List<Bill> bills = new ArrayList<>(asList(billFoo, billBar, billBaz));

        String actualResult = new LastBillsListFormatter().format(bills);

        assertEquals(expectedResult, actualResult);
        assertTrue(actualResult.startsWith("Here are your last bills:\n"), "expect text to be started from description");
    }
}
