package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

import static com.travelBill.Icons.*;


public class ManageEventScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        Response response = new Response("What would you like to do?");
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton closeEventButton = new ReplyKeyboardButton().setText(LOCKED + " Close event");
        ReplyKeyboardButton switchToClosedEventButton = new ReplyKeyboardButton().setText(SCROLL + " Switch to closed event");
        ReplyKeyboardButton goBackButton = new ReplyKeyboardButton().setText(LEFT_ARROW + " Back to the previous menu");

        keyboard.addRow(closeEventButton);
        keyboard.addRow(switchToClosedEventButton);
        keyboard.addRow(goBackButton);

        response.replyKeyboard = keyboard;
        return response;
    }
}
