package com.travelBill.telegram.scenario.group.bill.delete.request;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.EventService;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowBillsToDeleteScenario implements Scenario {
    private final EventService eventService;
    private final BillService billService;

    public ShowBillsToDeleteScenario(EventService eventService, BillService billService) {
        this.eventService = eventService;
        this.billService = billService;
    }

    @Override
    public Response execute(Request request) {
        Long telegramChatId = request.chatId;
        Long eventId = eventService.findByTelegramChatId(telegramChatId).getId();
        Long currentUserId = request.user.getId();
        ResponseBuilder responseBuilder;

        List<Bill> lastBills = billService.selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(currentUserId, eventId);

        if (lastBills.size() > 0) {
            responseBuilder = new ShowBillsToDeleteSuccessResponseBuilder();
            ((ShowBillsToDeleteSuccessResponseBuilder) responseBuilder).bills = lastBills;
        } else {
            responseBuilder = new ShowBillsToDeleteFailResponseBuilder();
        }

        return responseBuilder.build();
    }
}
