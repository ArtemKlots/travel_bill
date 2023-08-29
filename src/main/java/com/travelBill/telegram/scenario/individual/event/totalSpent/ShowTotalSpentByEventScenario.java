package com.travelBill.telegram.scenario.individual.event.totalSpent;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import com.travelBill.api.core.bill.statistic.UserSpentStatisticItem;
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

        List<CurrencyStatisticItem> totalStatistic = billService.showTotalSpentByEvent(event.getId());
        List<UserSpentStatisticItem> userStatistic = billService.showSpentByPersonByEvent(event.getId());

        Response response = new Response(ParseMode.MARKDOWN);

        if (totalStatistic.size() > 0) {
            String header = String.format("Total spends for event *%s*:\n", event.getTitle());
            String body = this.buildTotalStatisticBody(totalStatistic);
            String userStatisticHeader = "\n\n User's total spends:\n";
            String userStatisticBody = this.buildUserStatisticBody(userStatistic);

            response.message = header + body + userStatisticHeader + userStatisticBody;
        } else {
            response.message = "There are no added bills for your current event";
        }
        return response;
    }

    private String buildTotalStatisticBody(List<CurrencyStatisticItem> statistic) {
        return statistic.stream()
                .map(statisticItem -> String.format(" - %1.2f %s", statisticItem.amount, statisticItem.currency))
                .collect(Collectors.joining("\n"));
    }

    private String buildUserStatisticBody(List<UserSpentStatisticItem> statistic) {
        return statistic.stream()
                .map(statisticItem -> {
                    String name = statisticItem.getUser().getFullName();
                    String body = statisticItem.getStatistic()
                            .stream()
                            .map(userStatisticItem -> String.format(" - %1.2f %s", userStatisticItem.amount, userStatisticItem.currency))
                            .collect(Collectors.joining("\n"));
                    return  String.format("%s:\n%s", name, body);
                })
                .collect(Collectors.joining("\n"));
    }
}
