package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.debt.DebtService;
import com.travelBill.api.core.debt.DebtSumDto;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.travelBill.Icons.GLOWING_STAR;
import static java.util.Objects.isNull;

@Service
public class SelectDebtorScenario implements Scenario {
    private final DebtService debtService;

    public SelectDebtorScenario(DebtService debtService) {
        this.debtService = debtService;
    }


    @Override
    public Response execute(Request request) {
        List<DebtSumDto> debts = debtService.getBalanceForUser(request.user.getId());

        InlineKeyboardButton lastEventMembersButton = new InlineKeyboardButton().setText(GLOWING_STAR + "Show all contacts").setCallbackData("select_debtor-all_contacts");
        InlineKeyboardButton cancelButton = new InlineKeyboardButton().setText("Cancel").setCallbackData("delete_previous_message");
        Response response = new Response();
        response.inlineKeyboard = new InlineKeyboard();

        if (debts.size() == 0) {
            response.message = "You have neither debts nor debtors to send money";
        } else {

            String message = "Your balance: \n";
            Set<Map.Entry<Long, List<DebtSumDto>>> entrySet = debts.stream().collect(Collectors.groupingBy(DebtSumDto::getDebtorId)).entrySet();
            for (Map.Entry<Long, List<DebtSumDto>> entry : entrySet) {
                String firstName = isNull(entry.getValue().get(0).getUserFirstName()) ? "" : entry.getValue().get(0).getUserFirstName();
                String lastName = isNull(entry.getValue().get(0).getUserLastName()) ? "" : entry.getValue().get(0).getUserLastName();
                String fullName = firstName.concat(lastName).trim().concat(":\n");
                message = message.concat(fullName);

                for (DebtSumDto dto : entry.getValue()) {
                    message = message.concat(String.format("  %s \t%s\n", dto.getCurrency(), new DecimalFormat("#,###.00").format(dto.getAmount())));
                }
                message = message.concat("\n");

            }
            response.message = message + "Select recipient:";
            response.inlineKeyboard = new DebtorListKeyboardBuilder().buildByDebts(debts);
        }

        response.inlineKeyboard.addRow(lastEventMembersButton);
        response.inlineKeyboard.addRow(cancelButton);
        return response;
    }
}
