package com.travelBill.telegram.scenario.group;

import com.travelBill.api.core.user.User;
import com.travelBill.telegram.scenario.UnknownScenario;
import com.travelBill.telegram.scenario.bill.AddBillCommandParser;
import com.travelBill.telegram.scenario.bill.AddBillScenario;
import com.travelBill.telegram.scenario.common.context.BillContext;
import com.travelBill.telegram.scenario.common.context.ContextProvider;
import com.travelBill.telegram.scenario.common.context.EventContext;
import com.travelBill.telegram.scenario.common.scenario.EventScenarioHelper;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.util.Objects.isNull;

@Service
public class GroupScenarioFactory {
    private final ContextProvider contextProvider;

    public GroupScenarioFactory(ContextProvider contextProvider) {
        this.contextProvider = contextProvider;
    }

    public Scenario createScenario(Update update, User currentUser) {
        EventScenarioHelper eventScenarioHelper = new EventScenarioHelper(update);
        EventContext eventContext = contextProvider.getEventContext(update, currentUser);
        BillContext billContext = contextProvider.getBillContext(update, currentUser);
        Scenario selectedScenario = null;

        if (isChatCreated(update)) {
            selectedScenario = new CreateEventScenario(eventContext);
        }

        if (isContribution(update)) {
            selectedScenario = new AddBillScenario(billContext);
        }

        if (isCommand(update)) {

            if (eventScenarioHelper.isJoinEventsSignal()) {
                selectedScenario = new JoinEventScenario(eventContext);
            }
        }

        if (isNull(selectedScenario)) {
            selectedScenario = new UnknownScenario(update);
        }

        return selectedScenario;
    }

    private boolean isContribution(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String text = update.getMessage().getText();
            return AddBillCommandParser.isContribution(text);
        }
        return false;
    }


    // TODO: 19.01.19 can be extracted
    private boolean isCommand(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("/");
    }

    private boolean isChatCreated(Update update) {
        return update.hasMessage()
                && (!isNull(update.getMessage().getGroupchatCreated()) || !isNull(update.getMessage().getSuperGroupCreated()));
    }

}
