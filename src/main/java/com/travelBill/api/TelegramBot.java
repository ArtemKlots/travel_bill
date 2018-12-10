package com.travelBill.api;

import com.travelBill.api.core.Event;
import com.travelBill.api.event.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final EventService eventService;

    @Autowired
    public TelegramBot(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public void onUpdateReceived(Update update) {

        SendMessage message = null;
        if (update.getMessage() != null && update.getMessage().getText().equals("/start")) {
            message = sendStart(update);
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (callbackQuery != null) {
            if (callbackQuery.getData().equals("event_list")) {
                message = sendEvents(callbackQuery);
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
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<InlineKeyboardButton> firstInlineRow = new ArrayList<>();
        List<InlineKeyboardButton> secondInlineRow = new ArrayList<>();

        firstInlineRow.add(new InlineKeyboardButton().setText("See events list").setCallbackData("event_list"));
        secondInlineRow.add(new InlineKeyboardButton().setText("Create a new event").setCallbackData("create_new_event"));


        rowsInline.add(firstInlineRow);
        rowsInline.add(secondInlineRow);

        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);
        return message;
    }

    private SendMessage sendEvents(CallbackQuery callbackQuery) {
        long chat_id = callbackQuery.getMessage().getChatId();
        SendMessage message = new SendMessage().setChatId(chat_id);

        List<Event> events = eventService.getAll();
        String eventsTitles = events.stream()
                .map(Event::getTitle)
                .collect(Collectors.joining(", "));
        message.setText("Your events: \n" + eventsTitles);
        return message;
    }
}
