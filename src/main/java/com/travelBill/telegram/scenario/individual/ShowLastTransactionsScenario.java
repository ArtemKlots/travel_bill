package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.telegram.formatter.bill.BillListFormatter;
import com.travelBill.telegram.scenario.common.AbstractBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;


public class ShowLastTransactionsScenario extends AbstractBillScenario {
    private BillListFormatter formatter;

    ShowLastTransactionsScenario(BillContext billContext, BillListFormatter formatter) {
        super(billContext);
        this.formatter = formatter;
    }

    @Override
    public SendMessage createMessage() {
        List<Bill> bills = billContext.billService.selectByUserIdOrderByCreatedAt(billContext.currentUser.getId());
        SendMessage message = new SendMessage();

        String content = formatter.format(bills);

        return message.setChatId(billContext.getChatId())
                .setText(content);
    }
}
