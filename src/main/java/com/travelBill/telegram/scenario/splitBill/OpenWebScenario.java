package com.travelBill.telegram.scenario.splitBill;

import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;
import com.travelBill.telegram.driver.keyboard.inline.LoginUrl;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OpenWebScenario  implements Scenario {
    private ApplicationConfiguration applicationConfiguration;

    @Override
    public Response execute(Request request) {
        Response response = new Response();
        InlineKeyboard keyboard = new InlineKeyboard();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.text = "Click to login:";
        LoginUrl loginUrl = new LoginUrl();
        loginUrl.botUsername = applicationConfiguration.getTelegramBotMention();
        loginUrl.url = applicationConfiguration.getLoginUrl();
        loginUrl.forwardText = "Link is invalid!";

        button.loginUrl = loginUrl;

        keyboard.addRow(button);
        response.message = "Link:";
        response.inlineKeyboard = keyboard;
        return response;
    }

    @Autowired
    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }
}
