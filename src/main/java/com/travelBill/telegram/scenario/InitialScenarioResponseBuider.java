package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class InitialScenarioResponseBuider implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "What would you like to do?";
        response.keyboard = createMarkup();
        return response;
    }

    private ReplyKeyboard createMarkup() {
        ReplyKeyboardMarkup markup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow currentEventRow = new KeyboardRow();
        currentEventRow.add("Show current event");

        KeyboardRow seeEventsRow = new KeyboardRow();
        seeEventsRow.add("Show events");

        KeyboardRow lastTransactionRow = new KeyboardRow();
        lastTransactionRow.add("Show last transactions");

        keyboard.add(seeEventsRow);
//        keyboard.add(currentEventRow);
        keyboard.add(lastTransactionRow);

        markup.setKeyboard(keyboard);
        return markup;
    }
}
