package com.travelBill.telegram.scenario;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;

public class InitialScenarioResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "What would you like to do?";
        response.replyKeyboard = createKeyboard();
        return response;
    }

    private ReplyKeyboard createKeyboard() {
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton currentEventButton = new ReplyKeyboardButton().setText("Show current event");
        ReplyKeyboardButton switchEventButton = new ReplyKeyboardButton().setText("Switch event");
        ReplyKeyboardButton seeEventsButton = new ReplyKeyboardButton().setText("Show events");
        ReplyKeyboardButton lastTransactionButton = new ReplyKeyboardButton().setText("Show last transactions");

        keyboard.addRow(currentEventButton, switchEventButton);
        keyboard.addRow(seeEventsButton);
        keyboard.addRow(lastTransactionButton);
        return keyboard;
    }
}
