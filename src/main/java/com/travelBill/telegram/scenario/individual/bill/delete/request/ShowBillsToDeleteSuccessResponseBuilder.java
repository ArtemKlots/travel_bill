package com.travelBill.telegram.scenario.individual.bill.delete.request;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.ResponseBuilder;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;

import java.util.List;

public class ShowBillsToDeleteSuccessResponseBuilder implements ResponseBuilder {
    List<Bill> bills;

    @Override
    public Response build() {
        Response response = new Response();
        response.message = "Select bill from the list:";
        response.inlineKeyboard = createMarkup();
        return response;
    }

    private InlineKeyboard createMarkup() {
        InlineKeyboard inlineKeyboard = new InlineKeyboard();

        for (Bill bill : bills) {
            String rowText = String.format("%s %s %s", bill.getAmount(), bill.getCurrency(), bill.getPurpose());
            InlineKeyboardButton button = new InlineKeyboardButton().setText(rowText).setCallbackData("delete_bill_" + bill.getId());
            inlineKeyboard.addRow(button);
        }

        inlineKeyboard.addRow(new InlineKeyboardButton().setText("Cancel").setCallbackData("cancel_delete_bill"));

        return inlineKeyboard;
    }
}
