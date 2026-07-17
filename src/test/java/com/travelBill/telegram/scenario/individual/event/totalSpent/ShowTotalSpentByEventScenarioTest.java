package com.travelBill.telegram.scenario.individual.event.totalSpent;

import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.bill.statistic.CurrencyStatisticItem;
import com.travelBill.api.core.bill.statistic.UserSpentStatisticItem;
import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.individual.event.EventIsNotSelectedResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.travelBill.telegram.driver.ParseMode.MARKDOWN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowTotalSpentByEventScenarioTest {
    private BillService billServiceMock = mock(BillService.class);
    private ShowTotalSpentByEventScenario showTotalSpentByEventScenario;
    private Event eventMock = mock(Event.class);
    private Request request;
    private User user;

    @BeforeEach
    void setupContext() {
        showTotalSpentByEventScenario = new ShowTotalSpentByEventScenario(billServiceMock);
        request = new Request();
        user = new User();

        request.user = user;
    }


    @Test
    void execute_shouldReturnEventIsNotSelectedResponse_whenCurrentEventIsNotSelected() {
        Response result = showTotalSpentByEventScenario.execute(request);
        assertEquals(EventIsNotSelectedResponse.class, result.getClass());
    }

    @Test
    void execute_shouldNotifyAboutAbsentBills_whenEventHasNoBills() {
        when(eventMock.getId()).thenReturn(123L);
        when(billServiceMock.showTotalSpentByEvent(123L)).thenReturn(new ArrayList<>());
        user.setCurrentEvent(eventMock);

        Response result = showTotalSpentByEventScenario.execute(request);

        assertEquals("There are no added bills for your current event", result.message);
    }

    @Test
    void execute_shouldReturnSpentStatistic_whenEventHasBills() {
        List<CurrencyStatisticItem> statistic = Arrays.asList(
                new CurrencyStatisticItem("UAH", 100.0),
                new CurrencyStatisticItem("EUR", 200.0));

        when(eventMock.getId()).thenReturn(123L);
        when(eventMock.getTitle()).thenReturn("Event title");
        when(billServiceMock.showTotalSpentByEvent(123L)).thenReturn(statistic);
        when(billServiceMock.showSpentByPersonByEvent(123L)).thenReturn(new ArrayList<>());

        user.setCurrentEvent(eventMock);

        Response result = showTotalSpentByEventScenario.execute(request);

        assertEquals("Total spends for event *Event title*:\n - 100.00 UAH\n - 200.00 EUR\n\nUser's total spends:\n", result.message);
        assertEquals(MARKDOWN, result.parseMode);
    }

    @Test
    void execute_shouldIncludePerUserBreakdown_whenUserStatisticPresent() {
        List<CurrencyStatisticItem> totalStatistic = Arrays.asList(new CurrencyStatisticItem("UAH", 100.0));

        User john = new User();
        john.setFirstName("John");
        john.setLastName("Smith");
        UserSpentStatisticItem johnStatistic = new UserSpentStatisticItem(john,
                Arrays.asList(new CurrencyStatisticItem("UAH", 60.0)));

        User jane = new User();
        jane.setFirstName("Jane");
        jane.setLastName("Doe");
        UserSpentStatisticItem janeStatistic = new UserSpentStatisticItem(jane,
                Arrays.asList(new CurrencyStatisticItem("UAH", 40.0)));

        when(eventMock.getId()).thenReturn(123L);
        when(eventMock.getTitle()).thenReturn("Event title");
        when(billServiceMock.showTotalSpentByEvent(123L)).thenReturn(totalStatistic);
        when(billServiceMock.showSpentByPersonByEvent(123L)).thenReturn(Arrays.asList(johnStatistic, janeStatistic));

        user.setCurrentEvent(eventMock);

        Response result = showTotalSpentByEventScenario.execute(request);

        assertEquals("Total spends for event *Event title*:\n" +
                " - 100.00 UAH\n" +
                "\n" +
                "User's total spends:\n" +
                "John Smith:\n" +
                " - 60.00 UAH\n" +
                "Jane Doe:\n" +
                " - 40.00 UAH", result.message);
    }
}