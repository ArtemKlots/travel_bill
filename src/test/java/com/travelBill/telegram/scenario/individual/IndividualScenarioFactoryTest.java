package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.BillScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.NewEventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IndividualScenarioFactoryTest {
    private ContextProvider contextProvider = mock(ContextProvider.class);
    private NewEventScenarioHelper eventScenarioHelper = mock(NewEventScenarioHelper.class);
    private BillScenarioHelper billScenarioHelper = mock(BillScenarioHelper.class);
    private Update update = mock(Update.class);
    private User user = mock(User.class);

    @Spy
    EventContext eventContext;
    @Spy
    BillContext billContext;

    @Before
    void setupContextProvider() {
        when(contextProvider.getBillContext(any(), any())).thenReturn(billContext);
        when(contextProvider.getEventContext(any(), any())).thenReturn(eventContext);
    }

    @Test
    void createScenario_shouldReturnShowEventsScenario_whenShowEventsSignalWasProvided() {
        when(eventScenarioHelper.isShowEventsSignal(update)).thenReturn(true);

        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);
        Scenario scenario = factory.createScenario(update, user);

        assertEquals(ShowEventsListScenario.class, scenario.getClass());
    }

    @Test
    void createScenario_shouldReturnShowLastTransactionsScenario_whenShowLastTransactionsSignalWasProvided() {
        when(billScenarioHelper.isShowLastTransactionsSignal(update)).thenReturn(true);

        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);
        Scenario scenario = factory.createScenario(update, user);

        assertEquals(ShowLastTransactionsScenario.class, scenario.getClass());

    }

    @Test
    void createScenario_shouldThrowScenarioNotFoundException_whenShenarioWasNotFouns() {
        IndividualScenarioFactory factory = new IndividualScenarioFactory(contextProvider, eventScenarioHelper, billScenarioHelper);

        Exception expectedException = null;

        try {
            factory.createScenario(update, user);
        } catch (ScenarioNotFoundException e) {
            expectedException = e;
        }

        assertNotNull(expectedException);
    }

}