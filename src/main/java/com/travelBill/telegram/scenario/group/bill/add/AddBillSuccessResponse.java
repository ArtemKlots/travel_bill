package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class AddBillSuccessResponse implements Response {
    String transaction;
    User user;

    @Override
    public String getMessage() {
        return String.format("Done ;) %s were accepted from %s %s", transaction, user.getFirstName(), user.getLastName());
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
