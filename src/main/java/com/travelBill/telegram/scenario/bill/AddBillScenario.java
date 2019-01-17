package com.travelBill.telegram.scenario.bill;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class AddBillScenario extends AbstractBillScenario {
    AddBillScenario(BillContext billContext) {
        super(billContext);
    }

    @Override
    public SendMessage createMessage() {
        User user = billContext.currentUser;
        Event event = billContext.eventService.findByTelegramChatId(billContext.getChatId());
        String textMessage = billContext.update.getMessage().getText();
        String responseMessage = "Done ;)";

        try {
            Bill bill = AddBillCommandParser.parse(textMessage);
            bill.setEvent(event);
            bill.setUser(user);

            billContext.billService.save(bill);
        } catch (Exception e) {
            responseMessage = "Cannot add bill. Something went wrong";
            e.printStackTrace();
        }

        return new SendMessage()
                .setChatId(billContext.getChatId())
                .setText(responseMessage);
    }
}
