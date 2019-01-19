package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;
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

        InlineKeyboardMarkup markup = createMarkup(events);
        String messageText = getTextMessage(events);

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(messageText)
                .setReplyMarkup(markup);
    }

    private InlineKeyboardMarkup createMarkup(List<Event> events) {
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

    private String getTextMessage(List<Event> events) {
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
}
