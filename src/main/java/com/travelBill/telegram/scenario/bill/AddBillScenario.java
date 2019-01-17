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

        Bill bill = new Bill();
        bill.setEvent(event);
        bill.setUser(user);
        bill.setAmount(10);
        bill.setTitle("Some payment");

        billContext.billService.save(bill);

        String messageText = "Done ;)";

        return new SendMessage()
                .setChatId(billContext.getChatId())
                .setText(messageText);
    }
}
