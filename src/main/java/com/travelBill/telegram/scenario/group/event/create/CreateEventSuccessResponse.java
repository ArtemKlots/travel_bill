package com.travelBill.telegram.scenario.group.event.create;

import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;

public class CreateEventSuccessResponse implements Response {
    String eventTitle;

    @Override
    public String getMessage() {
        return String.format("Event %s has been successfully registered. " +
                "I have no access to members list, so I need to ask you to join manually. Please press /join ", eventTitle);
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return null;
    }
}
