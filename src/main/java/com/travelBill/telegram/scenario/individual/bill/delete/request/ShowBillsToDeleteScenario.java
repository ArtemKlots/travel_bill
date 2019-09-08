package com.travelBill.telegram.scenario.individual.bill.delete.request;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ShowBillsToDeleteScenario implements Scenario {
    private final BillService billService;

    public ShowBillsToDeleteScenario(BillService billService) {
        this.billService = billService;
    }

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        Long eventId = request.user.getCurrentEvent().getId();
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
