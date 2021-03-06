package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;

import java.util.List;

import static java.util.Objects.isNull;

public class ShowEventsListResponseBuilder implements ResponseBuilder {
    List<Event> events;


    @Override
    public Response build() {
        Response response = new Response();
        response.message = getMessage();
        if (events.size() > 0) {
            response.inlineKeyboard = getKeyboard();
        }
        return response;
    }

    public String getMessage() {
        if (isNull(events)) {
            throw new IllegalArgumentException("Events are not provided");
        }

        String messageText;
        switch (events.size()) {
            case (0):
                messageText = "Sorry, but you don't have event to switch";
                break;
            case (1):
                messageText = "Here is your event:";
                break;
            default:
                messageText = "Here are your events:";
        }
        return messageText;
    }

    public InlineKeyboard getKeyboard() {
        InlineKeyboard inlineKeyboard = new InlineKeyboard();

        for (Event event : events) {
            InlineKeyboardButton button = new InlineKeyboardButton().setText(event.getTitle()).setCallbackData("switch_to_event-" + event.getId());
            inlineKeyboard.addRow(button);
        }

        inlineKeyboard.addRow(new InlineKeyboardButton().setText("Cancel").setCallbackData("cancel_event_switching"));

        return inlineKeyboard;
    }
}
