package com.travelBill.telegram.scenario.splitBill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.travelBill.telegram.driver.Request;
import com.travelBill.telegram.driver.Response;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboard;
import com.travelBill.telegram.driver.keyboard.inline.InlineKeyboardButton;
import com.travelBill.telegram.driver.keyboard.inline.LoginUrl;
import com.travelBill.telegram.scenario.common.scenario.Scenario;
import org.springframework.stereotype.Service;

@Service
public class OpenWebScenario  implements Scenario {
    @Override
    public Response execute(Request request) {
        Response response = new Response();
        InlineKeyboard keyboard = new InlineKeyboard();
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.text = "Click to login:";
        LoginUrl loginUrl = new LoginUrl();
        loginUrl.botUsername = "Travel Bill";
        loginUrl.url = "http://google.com/";
        loginUrl.forwardText = "Link is invalid!";

        button.loginUrl = loginUrl;

        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("auth0")
                .sign(algorithm);
        response.message = "Link:";
        response.inlineKeyboard = keyboard;
        return response;
    }
}
