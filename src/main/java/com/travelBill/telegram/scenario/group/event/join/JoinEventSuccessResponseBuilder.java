package com.travelBill.telegram.scenario.group.event.join;

import com.travelBill.telegram.Response;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;

public class JoinEventSuccessResponseBuilder extends JoinEventResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = getMessage();
        response.replyKeyboard = getKeyboard();
        return response;
    }

    public String getMessage() {
        return String.format(
                "Hello %s! Now I know that you are here and can receive your contributions :) \n\n" +
                        "Use the following pattern to make a contribution (spaces are important!): \n<how much> <currency> <purpose> \n\n" +
                        "Example: 10 points to Gryffindor",
                member.getFullName());
    }

    public ReplyKeyboard getKeyboard() {
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton showDebtsButton = new ReplyKeyboardButton().setText("Show debts");

        keyboard.addRow(showDebtsButton);

        return keyboard;
    }
}
