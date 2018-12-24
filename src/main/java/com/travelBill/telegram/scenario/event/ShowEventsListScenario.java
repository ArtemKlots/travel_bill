package com.travelBill.telegram.scenario.event;

import com.travelBill.api.core.event.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ShowEventsListScenario extends AbstractEventScenario {
    ShowEventsListScenario(EventContext eventContext) {
        super(eventContext);
    }

    @Override
    public SendMessage createMessage() {
        Long userId = eventContext.currentUser.getId();
        List<Event> events = eventContext.eventService.getEventsByOwnerId(userId);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Event event : events) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText(event.getTitle()).setCallbackData("switch_to_event" + event.getId()));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);

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

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(messageText)
                .setReplyMarkup(markupInline);
    }
}
