package com.travelBill.api.telegram.scenario;

import com.travelBill.api.core.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ShowEventsListScenario {
    static SendMessage perform(EventContext eventContext) {
        List<Event> events = eventContext.telegramEventService.getAll();

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Event event : events) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(new InlineKeyboardButton().setText(event.getTitle()).setCallbackData("switch_to " + event.getId()));
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
                .setChatId(eventContext.chatId)
                .setText(messageText)
                .setReplyMarkup(markupInline);
    }
}
