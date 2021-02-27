package com.travelBill.telegram.scenario.individual.debt;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.driver.BotApi;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllContactsScenario implements Scenario {
    private final UserService userService;
    private final BotApi botApi;

    public GetAllContactsScenario(UserService userService, BotApi botApi) {
        this.userService = userService;
        this.botApi = botApi;
    }

    @Override
    public Response execute(Request request) {
        botApi.deleteMessage(request);
        // TODO: Make debt service and include here split bill debts
        List<User> users = userService.getAllContactedUsers(request.user.getId());

        if (users.size() == 0) {
            return new Response("You have not participated in any event. Contact list consists of members from previous events, so you need to create one or be invited to any event");
        } else {
            Response response = new Response("Select recipient:");
            response.inlineKeyboard = new DebtorListKeyboardBuilder().buildByUsers(users);
            return response;
        }
    }
}
