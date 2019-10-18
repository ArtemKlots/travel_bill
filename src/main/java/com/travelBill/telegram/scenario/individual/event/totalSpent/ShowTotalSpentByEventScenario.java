package com.travelBill.telegram.scenario.individual.event.totalSpent;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import com.travelBill.api.core.event.Event;
import com.travelBill.telegram.driver.ParseMode;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Service
public class ShowTotalSpentByEventScenario implements Scenario {
    private final BillService billService;

    public ShowTotalSpentByEventScenario(BillService billService) {
        this.billService = billService;
    }

    @Override
    public Response execute(Request request) {
        Event event = request.user.getCurrentEvent();
        if (isNull(event)) {
            return new EventIsNotSelectedResponse();
        }

        List<CurrencyStatisticItem> statistic = billService.showTotalSpentByEvent(event.getId());

        Response response = new Response(ParseMode.MARKDOWN);

        if (statistic.size() > 0) {
            String header = String.format("Total spends for event *%s*:\n", event.getTitle());
            String body = statistic.stream()
                    .map(statisticItem -> String.format(" - %1.2f %s", statisticItem.amount, statisticItem.currency))
                    .collect(Collectors.joining("\n"));
            response.message = header + body;
        } else {
            response.message = "There are no added bills for your current event";
        }
        return response;
    }
}
