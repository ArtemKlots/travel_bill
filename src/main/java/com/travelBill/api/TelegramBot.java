package com.travelBill.api;

import com.travelBill.api.core.Event;
import com.travelBill.api.core.User;
import com.travelBill.api.event.EventService;
import com.travelBill.api.telegram.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final EventService eventService;
    private final TelegramUserService telegramUserService;

    @Autowired
    public TelegramBot(EventService eventService, TelegramUserService telegramUserService) {
        this.eventService = eventService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = null;
        if (update.getMessage() != null) {
            User user = telegramUserService.setupUser(update.getMessage().getFrom());

            if (update.getMessage().getText().equals("/start")) {
                message = sendStart(update);
            }

            if (update.getMessage().getText().toLowerCase().equals("show events")) {
                message = sendEvents(update);
            }
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "Bot username";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_KEY");
    }

    private SendMessage sendStart(Update update) {
        long chat_id = update.getMessage().getChatId();

        SendMessage message = new SendMessage()
                .setChatId(chat_id)
                .setText("What would you like to do?");

        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow createEventRow = new KeyboardRow();
        createEventRow.add("Create event");

        KeyboardRow seeEventsRow = new KeyboardRow();
        seeEventsRow.add("Show events");

        keyboard.add(createEventRow);
        keyboard.add(seeEventsRow);

        markup.setKeyboard(keyboard);

        message.setReplyMarkup(markup);

        return message;
    }

    private SendMessage sendEvents(Update update) {
        long chat_id = update.getMessage().getChatId();

        List<Event> events = eventService.getAll();

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
                .setChatId(chat_id)
                .setText(messageText)
                .setReplyMarkup(markupInline);
    }
}
