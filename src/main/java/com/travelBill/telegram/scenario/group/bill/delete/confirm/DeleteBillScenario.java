package com.travelBill.telegram.scenario.group.bill.delete.confirm;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;

import static org.telegram.telegrambots.meta.api.methods.ParseMode.MARKDOWN;

public class DeleteBillScenario extends AbstractBillScenario {

    private static final int BILL_ID_INDEX_IN_CALLBACK = 2;

    public DeleteBillScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public Response execute() {
        String stringBillId = billContext.request.callbackQueryData.split("_")[BILL_ID_INDEX_IN_CALLBACK];
        Long billId = Long.parseLong(stringBillId);
        Bill billToRemove = billContext.billService.findById(billId);

        billContext.billService.delete(billToRemove, billContext.currentUser);
        billContext.botApi.deleteMessage(billContext.getChatId(), billContext.request.messageId);

        DeleteBillResponse response = new DeleteBillResponse();
        response.bill = billToRemove;
        response.actorFullName = billContext.currentUser.getFullName();
        response.parseMode = MARKDOWN;

        return response;
    }

}
