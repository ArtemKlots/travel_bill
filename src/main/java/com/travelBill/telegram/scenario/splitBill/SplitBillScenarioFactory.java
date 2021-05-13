package com.travelBill.telegram.scenario.splitBill;

import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.scenario.InitialScenario;
import com.travelBill.telegram.scenario.common.ScenarioNotFoundException;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.travelBill.telegram.UserCommand.*;

@Service
public class SplitBillScenarioFactory {
    private SwitchToSplitBillScenario switchToSplitBillScenario;
    private OpenWebScenario openWebScenario;
    private InitialScenario initialScenario;

    public Scenario createScenario(Request request) throws ScenarioNotFoundException {
        Scenario selectedScenario = null;

        if (request.hasMessage() && request.message.contains(SWITCH_TO_SPLIT_BILL)) {
            selectedScenario = switchToSplitBillScenario;
        }

        if (request.hasMessage() && request.message.contains(OPEN_WEB)) {
            selectedScenario = openWebScenario;
        }

        // TODO: return correct keyboard on 1:1 back
        if (request.hasMessage() && request.message.contains(SWITCH_TO_TRAVEL_BILL)) {
            selectedScenario = initialScenario;
        }

        return selectedScenario;
    }

    @Autowired
    public void setSwitchToSplitBillScenario(SwitchToSplitBillScenario switchToSplitBillScenario) {
        this.switchToSplitBillScenario = switchToSplitBillScenario;
    }

    @Autowired
    public void setInitialScenario(InitialScenario initialScenario) {
        this.initialScenario = initialScenario;
    }

    @Autowired
    public void setOpenWebScenario(OpenWebScenario openWebScenario) {
        this.openWebScenario = openWebScenario;
    }
}
