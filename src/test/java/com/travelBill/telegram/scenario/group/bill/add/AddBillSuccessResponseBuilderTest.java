package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddBillSuccessResponseBuilderTest {
    private AddBillSuccessResponseBuilder builder;
    private User user;

    @BeforeEach
    void setupContext() {
        builder = new AddBillSuccessResponseBuilder();
        user = new User();
        builder.user = user;
    }

    @Test
    void build_shouldReturnResponseWithBillCurrencyAndAmountAndUserFullName_whenBillAndUserProvided() {
        user.setFirstName("Tywin");
        user.setLastName("Lannister");

        builder.transaction = "300 Gold Dragon coin for soldiers of fortune";

        Response response = builder.build();
        assertEquals("Done ;) 300 Gold Dragon coin for soldiers of fortune were accepted from Tywin Lannister", response.message);
    }

    @Test
    void build_shouldReturnResponseWithBillCurrencyAndAmountAndUserName_whenUserDontHaveSurname() {
        user.setLastName("Tywin");

        builder.transaction = "300 Gold Dragon coin for soldiers of fortune";

        Response response = builder.build();
        assertEquals("Done ;) 300 Gold Dragon coin for soldiers of fortune were accepted from Tywin", response.message);
    }

}