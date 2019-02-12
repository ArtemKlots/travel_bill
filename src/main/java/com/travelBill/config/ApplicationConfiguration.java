package com.travelBill.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@ConfigurationProperties
@Component
@Validated
public class ApplicationConfiguration {
    @Value("${TELEGRAM_KEY}")
    @NotEmpty
    private String telegramKey;


    public String getTelegramKey() {
        return telegramKey;
    }

    public void setTelegramKey(String telegramKey) {
        this.telegramKey = telegramKey;
    }
}
