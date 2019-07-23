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
import java.util.stream.Collectors;

public class JoinEventScenario extends AbstractEventScenario {
    public JoinEventScenario(EventContext eventContext) {
        super(eventContext);
    }

    // TODO: 11.06.19 Refactor it
    public SendMessage createMessage() {
        Event event = eventContext.eventService.findByTelegramChatId(eventContext.getChatId());

        if (isNewMember(event, eventContext)) {

            eventContext.eventService.addMember(event, eventContext.currentUser);

            String responseMessage = String.format(
                    "Hello %s! Now I know that you are here and can receive your contributions :) \n\n" +
                            "Use the following pattern to make a contribution (spaces are important!): \n<how much> <currency> <purpose> \n\n" +
                            "Example: 10 points to gryffindor",
                    eventContext.currentUser.getFullName()
            );

            return new SendMessage()
                    .setChatId(eventContext.getChatId())
                    .setText(responseMessage)
                    .setReplyMarkup(createMarkup());
        } else {
            String message = String.format("Hmm... It seems I saw you in this chat. You are %s, right?",
                    eventContext.currentUser.getFullName());

            return new SendMessage()
                    .setChatId(eventContext.getChatId())
                    .setText(message);
        }
    }

    private ReplyKeyboard createMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow showDebtsRow = new KeyboardRow();
        showDebtsRow.add("Show debts");

        KeyboardRow deleteBillRow = new KeyboardRow();
        deleteBillRow.add("Delete bill");

        keyboard.add(showDebtsRow);
        keyboard.add(deleteBillRow);

        markup.setKeyboard(keyboard);
        markup.setResizeKeyboard(true);
        return markup;
    }

    private boolean isNewMember(Event event, EventContext eventContext) {
        List matchedUser = event.getMembers()
                .stream()
                .filter(u -> u.getId().equals(eventContext.currentUser.getId()))
                .collect(Collectors.toList());

        return matchedUser.size() == 0;
    }
}
