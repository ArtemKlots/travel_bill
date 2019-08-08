package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class AddBillFailResponse implements Response {
    @Override
    public String getMessage() {
        return "Cannot add bill. Something went wrong";
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
