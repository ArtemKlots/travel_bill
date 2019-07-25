package com.travelBill.telegram;

import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.http.HttpService;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class BotApi {
    private final ApplicationConfiguration applicationConfiguration;
    private static final String DELETE_MESSAGE_RESOURCE = "deleteMessage";

    private static final String API_HOST = "https://api.telegram.org/";
    private final HttpService httpService;

    public BotApi(ApplicationConfiguration applicationConfiguration, HttpService httpService) {
        this.applicationConfiguration = applicationConfiguration;
        this.httpService = httpService;
    }

    public void deleteMessage(Long chatId, Integer messageId) throws IOException {
        List<NameValuePair> params = new ArrayList<>(2);
        params.add(new BasicNameValuePair("chat_id", chatId.toString()));
        params.add(new BasicNameValuePair("message_id", messageId.toString()));
        httpService.post(getApiUrl() + DELETE_MESSAGE_RESOURCE, params);
    }

    private String getApiUrl() {
        return API_HOST + "bot" + this.applicationConfiguration.getTelegramKey() + "/";
    }

}
