package com.travelBill.telegram.scenario.individual.bill.add;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddBillSuccessResponseBuilderTest {
    private AddBillSuccessResponseBuilder builder;
    private User user;
    private Bill bill;

    @BeforeEach
    void setupContext() {
        builder = new AddBillSuccessResponseBuilder();
        user = new User();
        bill = new Bill();
        builder.user = user;
        builder.bill = bill;
    }

    @Test
    void build_shouldReturnResponseWithBillCurrencyAndAmountAndUserFullName_whenBillAndUserProvided() {
        user.setFirstName("Tywin");
        user.setLastName("Lannister");

        bill.setAmount(300);
        bill.setCurrency("Gold Dragon coins");
        bill.setPurpose("for soldiers of fortune");

        Response response = builder.build();
        assertEquals("Done ;) *300 Gold Dragon coins* for soldiers of fortune were accepted from *Tywin Lannister*", response.message);
    }

    @Test
    void build_shouldReturnResponseWithBillCurrencyAndAmountAndUserName_whenUserDontHaveSurname() {
        user.setLastName("Tywin");

        bill.setAmount(300);
        bill.setCurrency("Gold Dragon coins");
        bill.setPurpose("for soldiers of fortune");

        Response response = builder.build();
        assertEquals("Done ;) *300 Gold Dragon coins* for soldiers of fortune were accepted from *Tywin*", response.message);
    }

}