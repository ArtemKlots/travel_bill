package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CreateEventFailResponse implements Response {
    @Override
    public String getMessage() {
        return "Sorry, something went wrong. I can do nothing in this chat";
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
