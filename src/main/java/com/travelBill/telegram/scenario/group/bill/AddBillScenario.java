package com.travelBill.telegram.scenario.group.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;

public class AddBillScenario extends AbstractBillScenario {
    public AddBillScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public Response execute() {
        User user = billContext.currentUser;
        Event event = billContext.eventService.findByTelegramChatId(billContext.getChatId());
        String textMessage = billContext.request.message;
        String responseMessage = String.format("Done ;) %s were accepted from %s %s", textMessage, user.getFirstName(), user.getLastName());

        try {
            Bill bill = AddBillCommandParser.parse(textMessage);
            bill.setEvent(event);
            bill.setUser(user);

            billContext.billService.save(bill);
        } catch (Exception e) {
            responseMessage = "Cannot add bill. Something went wrong";
            e.printStackTrace();
        }

        return new Response(responseMessage);
    }
}
