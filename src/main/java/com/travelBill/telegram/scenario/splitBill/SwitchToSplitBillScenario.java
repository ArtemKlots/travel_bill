package com.travelBill.telegram.scenario.splitBill;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import static com.travelBill.Icons.*;
import static com.travelBill.telegram.UserCommand.*;

@Service
public class SwitchToSplitBillScenario implements Scenario {
    @Override
    public Response execute(Request request) {
        //TODO: Add friendly message
        Response response = new Response("Done");
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton sendMoneyButton = new ReplyKeyboardButton().setText(ONE_TO_ONE_MENU);
        ReplyKeyboardButton openWebButton = new ReplyKeyboardButton().setText(GLOBBE_WITH_MERIDIANS + OPEN_WEB);
        ReplyKeyboardButton switchToSplitBill = new ReplyKeyboardButton().setText(GREEN_HEART + SWITCH_TO_TRAVEL_BILL);

        keyboard.addRow(sendMoneyButton, openWebButton);
        keyboard.addRow(switchToSplitBill);
        response.replyKeyboard = keyboard;
        return response;
    }
}
