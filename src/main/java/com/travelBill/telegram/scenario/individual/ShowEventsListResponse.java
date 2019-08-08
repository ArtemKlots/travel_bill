package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

public class ShowEventsListResponse implements Response {
    List<Event> events;

    @Override
    public String getMessage() {
        if (isNull(events)) {
            throw new IllegalArgumentException("Events are not provided");
        }

        String messageText;
        switch (events.size()) {
            case (0):
                messageText = "Sorry, but you still have no events";
                break;
            case (1):
                messageText = "Here is your event:";
                break;
            default:
                messageText = "Here are your events:";
        }
        return messageText;
    }

    @Override
    public ReplyKeyboard getKeyboard() {
        return createMarkup();
    }

    private InlineKeyboardMarkup createMarkup() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Event event : events) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText(event.getTitle()).setCallbackData("switch_to_event" + event.getId()));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
