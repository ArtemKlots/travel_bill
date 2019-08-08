package com.travelBill.telegram.scenario.group.bill.delete.request;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;

import java.util.List;

public class ShowBillsToDeleteScenario extends AbstractBillScenario {

    public ShowBillsToDeleteScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public Response execute() {
        Long telegramChatId = billContext.getChatId();
        Long eventId = billContext.eventService.findByTelegramChatId(telegramChatId).getId();
        Long currentUserId = billContext.currentUser.getId();
        Response response;

        List<Bill> lastBills = billContext.billService.selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(currentUserId, eventId);

        if (lastBills.size() > 0) {
            response = new ShowBillsToDeleteSuccessResponse();
        } else {
            response = new ShowBillsToDeleteFailResponse();
        }

        return response;
    }
}
