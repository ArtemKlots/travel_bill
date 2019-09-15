package com.travelBill.telegram.scenario;

import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboard;
import com.travelBill.telegram.driver.keyboard.reply.ReplyKeyboardButton;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;

public class InitialScenarioResponseBuilder implements ResponseBuilder {

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Hey there! Let's travel\n" +
                "1. Join the event or create a new one by adding the bot to your group chat\n" +
                "2. Use the following pattern to make a contribution (spaces are important!): \n<*how much*> <*currency*> <*purpose*> \n" +
                "Example: 10 points to Gryffindor\n" +
                "3. Ask me to calculate your debts and I'll do it for you";
        response.replyKeyboard = createKeyboard();
        response.parseMode = MARKDOWN;
        return response;
    }

    private ReplyKeyboard createKeyboard() {
        ReplyKeyboard keyboard = new ReplyKeyboard();

        ReplyKeyboardButton currentEventButton = new ReplyKeyboardButton().setText("Show current event");
        ReplyKeyboardButton switchEventButton = new ReplyKeyboardButton().setText("Switch event");
        ReplyKeyboardButton showLastBillsButton = new ReplyKeyboardButton().setText("Show last bills");
        ReplyKeyboardButton showDebtsButton = new ReplyKeyboardButton().setText("Show debts");
        ReplyKeyboardButton deleteBillButton = new ReplyKeyboardButton().setText("Delete bill");

        keyboard.addRow(currentEventButton, switchEventButton);
        keyboard.addRow(deleteBillButton, showDebtsButton, showLastBillsButton);
//        keyboard.addRow();
//        keyboard.addRow();
        return keyboard;
    }
}
