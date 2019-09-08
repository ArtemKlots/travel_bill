package com.travelBill.telegram.scenario.individual.bill.add;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.Request;
import com.travelBill.telegram.Response;
import com.travelBill.telegram.ResponseBuilder;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.group.bill.AddBillCommandParser;
import com.travelBill.telegram.scenario.individual.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.isNull;

@Service
public class AddBillScenario implements Scenario {
    private final BillService billService;
    private final EventService eventService;

    public AddBillScenario(BillService billService, EventService eventService) {
        this.billService = billService;
        this.eventService = eventService;
    }

    @Override
    public Response execute(Request request) {
        if (isNull(request.user.getCurrentEvent())) {
            return new EventIsNotSelectedResponse();
        }

        User user = request.user;
        Event event = eventService.findById(request.user.getCurrentEvent().getId());
        String textMessage = request.message;
        ResponseBuilder responseBuilder;

        try {
            Bill bill = AddBillCommandParser.parse(textMessage);
            bill.setEvent(event);
            bill.setUser(user);
            billService.save(bill);

            responseBuilder = new AddBillSuccessResponseBuilder();
            ((AddBillSuccessResponseBuilder) responseBuilder).bill = bill;
            ((AddBillSuccessResponseBuilder) responseBuilder).user = user;
        } catch (Exception e) {
            responseBuilder = new AddBillFailResponseBuilder();
            e.printStackTrace();
        }

        return responseBuilder.build();
    }
}
