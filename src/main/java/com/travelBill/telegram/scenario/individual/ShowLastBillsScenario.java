package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
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
