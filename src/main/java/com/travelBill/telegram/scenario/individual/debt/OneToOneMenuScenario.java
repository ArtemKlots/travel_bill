package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;

import static com.travelBill.telegram.UserCommand.*;

public class OneToOneMenuScenario implements Scenario {

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton sendMoneyButton = new ReplyKeyboardButton().setText(SEND_MONEY);
        ReplyKeyboardButton showHistoryButton = new ReplyKeyboardButton().setText(ONE_TO_ONE_HISTORY);
        ReplyKeyboardButton goBackButton = new ReplyKeyboardButton().setText(GO_BACK);

        keyboard.addRow(sendMoneyButton);
        keyboard.addRow(showHistoryButton);
        keyboard.addRow(goBackButton);

        response.message = "Select an operation";
        response.replyKeyboard = keyboard;
        return response;
    }
}
