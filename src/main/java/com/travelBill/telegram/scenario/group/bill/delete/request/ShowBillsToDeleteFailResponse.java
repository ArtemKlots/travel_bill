package com.travelBill.telegram.scenario.group.bill.delete.request;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class ShowBillsToDeleteFailResponse implements Response {

    @Override
    public String getMessage() {
        return "Нou haven't added any bill to this event";
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
