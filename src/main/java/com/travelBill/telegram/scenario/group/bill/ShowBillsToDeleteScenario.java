package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.TelegramUpdateUtils;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class ShowBillsToDeleteScenario extends AbstractBillScenario {

    public ShowBillsToDeleteScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public SendMessage createMessage() {
        Long telegramChatId = new TelegramUpdateUtils().getChatId(billContext.update);
        Long eventId = billContext.eventService.findByTelegramChatId(telegramChatId).getId();
        Long currentUserId = billContext.currentUser.getId();

        List<Bill> lastBills = billContext.billService.selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(currentUserId, eventId);

        SendMessage message = new SendMessage().setChatId(telegramChatId);

        if (lastBills.size() > 0) {
            message.setText("Select bill from the list:")
                    .setReplyMarkup(createMarkup(lastBills));
        } else {
            message.setText("Нou haven't added any bill to this event");
        }

        return message;
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
