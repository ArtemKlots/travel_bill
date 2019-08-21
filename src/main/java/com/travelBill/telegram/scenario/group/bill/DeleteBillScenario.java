package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.exceptions.TravelBillException;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class DeleteBillScenario extends AbstractBillScenario {

    private static final int BILL_ID_INDEX_IN_CALLBACK = 2;

    public DeleteBillScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public SendMessage createMessage() {
        Long telegramChatId = billContext.getChatId();
        String stringBillId = billContext.update.getCallbackQuery().getData().split("_")[BILL_ID_INDEX_IN_CALLBACK];
        Long billId = Long.parseLong(stringBillId);
        Bill billToRemove = billContext.billService.findById(billId);
        String resultText;

        try {
            billContext.billService.delete(billToRemove, billContext.currentUser);
            billContext.botApi.deleteMessage(billContext.getChatId(), billContext.getMessageId());
            resultText = String.format("*%s %s %s* has been successfully removed by *%s*",
                    billToRemove.getAmount(), billToRemove.getCurrency(), billToRemove.getPurpose(), billContext.currentUser.getFullName());
        } catch (TravelBillException e) {
            resultText = e.getMessage();
        }

        return new SendMessage()
                .setChatId(telegramChatId)
                .setText(resultText)
                .setParseMode(MARKDOWN);
    }

}
