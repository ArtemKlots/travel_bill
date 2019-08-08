package com.travelBill.telegram.scenario.group.bill.add;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.group.bill.AddBillCommandParser;

public class AddBillScenario extends AbstractBillScenario {
    public AddBillScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public Response execute() {
        User user = billContext.currentUser;
        Event event = billContext.eventService.findByTelegramChatId(billContext.getChatId());
        String textMessage = billContext.request.message;
        Response response;

        try {
            Bill bill = AddBillCommandParser.parse(textMessage);
            bill.setEvent(event);
            bill.setUser(user);
            billContext.billService.save(bill);

            response = new AddBillSuccessResponse();
            ((AddBillSuccessResponse) response).transaction = textMessage;
            ((AddBillSuccessResponse) response).user = user;
        } catch (Exception e) {
            response = new AddBillFailResponse();
            e.printStackTrace();
        }

        return response;
    }
}
