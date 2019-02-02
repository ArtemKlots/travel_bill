package com.travelBill.telegram.scenario.group.event;

import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.AbstractEventScenario;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class JoinEventScenario extends AbstractEventScenario {
    public JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    public SendMessage createMessage() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());

        eventContext.eventService.addMember(event, eventContext.currentUser);

        String responseMessage = String.format(
                "Hello %s %s! Now I know that you are here and can receive your contributions :)",
                eventContext.currentUser.getFirstName(),
                eventContext.currentUser.getLastName()
        );

        return new SendMessage()
                .setChatId(eventContext.getChatId())
                .setText(responseMessage)
                .setReplyMarkup(createMarkup());
    }

    private ReplyKeyboard createMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow showDebtsRow = new KeyboardRow();
        showDebtsRow.add("Show debts");

        keyboard.add(showDebtsRow);

        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        return markup;
    }
}
