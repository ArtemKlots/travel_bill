package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;

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

        if (billToRemove.getUser().getId().equals(billContext.currentUser.getId())) {
            try {
                billContext.billService.deleteById(billId);
                resultText = String.format("*%s %s %s* has been successfully removed by *%s*",
                        billToRemove.getAmount(), billToRemove.getCurrency(), billToRemove.getPurpose(), billContext.currentUser.getFullName());
            } catch (Exception e) {
                resultText = "Something went wrong :( Probably bill was not deleted";
            }
        } else {
            resultText = String.format("Hmm.. %s, it seems it is not your bill", billContext.currentUser.getFullName());
        }

        deleteDebtList(billContext);

        return new SendMessage()
                .setChatId(telegramChatId)
                .setText(resultText)
                .setParseMode("Markdown");
    }

    private void deleteDebtList(BillContext billContext) {
        Long telegramChatId = billContext.getChatId();
        Integer messageId = billContext.update.getCallbackQuery().getMessage().getMessageId();
        try {
            billContext.botApi.deleteMessage(telegramChatId, messageId);
        } catch (IOException e) {
            System.out.println("Error during message deleting");
            e.printStackTrace();
        }
    }
}
