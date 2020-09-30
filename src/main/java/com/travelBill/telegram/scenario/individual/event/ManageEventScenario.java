package com.travelBill.telegram.scenario.individual.event;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

import static com.travelBill.telegram.UserCommand.*;


public class ManageEventScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        Response response = new Response("What would you like to do?");
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton closeEventButton = new ReplyKeyboardButton().setText(CLOSE_EVENT);
        ReplyKeyboardButton switchToClosedEventButton = new ReplyKeyboardButton().setText(SWITCH_TO_CLOSED_EVENT);
        ReplyKeyboardButton goBackButton = new ReplyKeyboardButton().setText(GO_BACK);

        keyboard.addRow(closeEventButton);
        keyboard.addRow(switchToClosedEventButton);
        keyboard.addRow(goBackButton);

        response.replyKeyboard = keyboard;
        return response;
    }
}
