package com.travelBill.telegram.scenario.individual;

import com.travelBill.api.core.bill.Bill;
import com.travelBill.api.core.bill.BillService;
import com.travelBill.api.core.bill.BillServiceImpl;
import com.travelBill.api.core.user.User;
import com.travelBill.telegram.formatter.bill.LastTransactionsListFormatter;
import com.travelBill.telegram.scenario.common.context.BillContext;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShowLastTransactionsScenarioTest {
    private BillService billService = Mockito.mock(BillServiceImpl.class);
    private BillContext billContext;
    private User user;
    private LastTransactionsListFormatter formatter = mock(LastTransactionsListFormatter.class);
    private final String mockedFormatterResult = "$$$";

    private EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder().build();

    @BeforeEach
    void init() {
        user = random.nextObject(User.class);
        billContext = new BillContext();
        billContext.billService = billService;
        billContext.currentUser = user;
        billContext.update = random.nextObject(Update.class);
        when(formatter.format(any())).thenReturn(mockedFormatterResult);
    }

    @Test
    void showLastTransactionsScenario_shouldReturnMessage_forFoundTransactions() {
        List<Bill> bills = EnhancedRandom.randomListOf(5, Bill.class);
        when(billService.selectTop10ByUserIdOrderByCreatedAtDesc(user.getId())).thenReturn(bills);

        ShowLastTransactionsScenario scenario = new ShowLastTransactionsScenario(billContext, formatter);
        SendMessage message = scenario.createMessage();

        assertNotNull(message);
        assertEquals(mockedFormatterResult, message.getText());
    }


    @Test
    void showLastTransactionsScenario_shouldReturn_validSendMessage() {
        Bill bill = random.nextObject(Bill.class);
        when(billService.selectTop10ByUserIdOrderByCreatedAtDesc(user.getId())).thenReturn(Collections.singletonList(bill));

        SendMessage resultMessage = new ShowLastTransactionsScenario(billContext, formatter).createMessage();

        try {
            resultMessage.validate();
        } catch (TelegramApiException e) {
            assertNull(e, "assert validate() does not throw TelegramApiException");
        }
    }

    @Test
    void showLastTransactionsScenario_shouldReturnMessageWithTransactions_forUser() {

    }

    // Looks like is is not a responsibility of the scenario
    @Test
    void showLastTransactionsScenario_shouldReturnMessageWithSourceChatId() {
        Bill bill = random.nextObject(Bill.class);
        when(billService.selectTop10ByUserIdOrderByCreatedAtDesc(any())).thenReturn(Collections.singletonList(bill));

        String expectedChatId = billContext.getChatId().toString();
        String actualChatId = new ShowLastTransactionsScenario(billContext, formatter).createMessage().getChatId();

        assertEquals(expectedChatId, actualChatId);
    }

    @Test
    void showLastTransactionsScenario_shouldGetOnlyLastTransactions() {
        new ShowLastTransactionsScenario(billContext, formatter).createMessage();

        verify(billService, times(1)).selectTop10ByUserIdOrderByCreatedAtDesc(any());
    }

    @Test
    void showLastTransactionsScenario_shouldGetOnlyLastTransactions_forConcreteUser() {
        new ShowLastTransactionsScenario(billContext, formatter).createMessage();

        verify(billService, times(1)).selectTop10ByUserIdOrderByCreatedAtDesc(user.getId());
    }
}
