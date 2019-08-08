package com.travelBill.telegram.scenario.group.bill.delete.request;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ShowBillsToDeleteSuccessResponse extends Response {

    @Override
    public String getMessage() {
        return "Select bill from the list:";
    }

    private InlineKeyboardMarkup createMarkup(List<Bill> bills) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        for (Bill bill : bills) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            String rowText = String.format("%s %s %s", bill.getAmount(), bill.getCurrency(), bill.getPurpose());
            row.add(new InlineKeyboardButton().setText(rowText).setCallbackData("delete_bill_" + bill.getId()));
            rowsInline.add(row);
        }

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}
