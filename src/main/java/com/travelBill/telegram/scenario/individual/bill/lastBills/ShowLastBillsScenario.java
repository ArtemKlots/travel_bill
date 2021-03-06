package com.travelBill.telegram.scenario.individual.bill.lastBills;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Objects.isNull;


@Service
public class ShowLastBillsScenario implements Scenario {
    private final BillService billService;

    ShowLastBillsScenario(BillService billService) {
        this.billService = billService;
    }

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        List<Bill> bills = billService.selectTop10ByUserIdAndEventIdOrderByCreatedAtDesc(request.user.getId(), request.user.getCurrentEvent().getId());
        ShowLastBillsResponseBuilder responseBuilder = new ShowLastBillsResponseBuilder();
        responseBuilder.bills = bills;
        return responseBuilder.build();
    }
}
