package com.travelBill.telegram.scenario.common.scenario;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class BillScenarioHelperTest {
    private Update update;
    private Message message;

    @BeforeEach
    void initUpdate() {
        update = mock(Update.class);
        message = mock(Message.class);
    }

    @Test
    void isShowLastTransactionsSignal_shouldReturnTrue_ifMessageIs_show_last_transactions() {
        when(message.hasText()).thenReturn(true);
        when(message.getText()).thenReturn("Show last transactions");
        when(update.getMessage()).thenReturn(message);
        when(update.hasMessage()).thenReturn(true);

        BillScenarioHelper billScenarioHelper = new BillScenarioHelper();
        assertTrue(billScenarioHelper.isShowLastTransactionsSignal(update));
    }

    @Test
    void isShowLastTransactionsSignal_shouldReturnFalse_ifUpdateDoesntHaveMessage() {
        when(update.getMessage()).thenReturn(null);
        when(update.hasMessage()).thenReturn(false);

        BillScenarioHelper billScenarioHelper = new BillScenarioHelper();
        assertFalse(billScenarioHelper.isShowLastTransactionsSignal(update));
    }

    @Test
    void isShowLastTransactionsSignal_shouldReturnFalse_ifUpdateHaveMessageWithoutText() {
        when(message.hasText()).thenReturn(false);
        when(message.getText()).thenReturn(null);
        when(update.getMessage()).thenReturn(message);
        when(update.hasMessage()).thenReturn(true);

        BillScenarioHelper billScenarioHelper = new BillScenarioHelper();
        assertFalse(billScenarioHelper.isShowLastTransactionsSignal(update));
    }

    @Test
    void isShowLastTransactionsSignal_shouldReturnFalse_ifTextIsNotRelatedToBills() {
        when(message.hasText()).thenReturn(false);
        when(message.getText()).thenReturn("Foo");
        when(update.getMessage()).thenReturn(message);
        when(update.hasMessage()).thenReturn(true);

        BillScenarioHelper billScenarioHelper = new BillScenarioHelper();
        assertFalse(billScenarioHelper.isShowLastTransactionsSignal(update));
    }

    @Test
    void isBillAction_shouldReturnTrue_ifShowLastTransactionsSignalProvided() {
        BillScenarioHelper billScenarioHelper = new BillScenarioHelper();
        BillScenarioHelper billScenarioHelperSpy = spy(billScenarioHelper);

        doReturn(true).when(billScenarioHelperSpy).isShowLastTransactionsSignal(update);

        assertTrue(billScenarioHelperSpy.isBillAction(update));
    }


}
