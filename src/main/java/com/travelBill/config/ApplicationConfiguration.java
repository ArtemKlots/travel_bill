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

    @Value("${LIFECYCLE_URL}")
    private String lifecycleUrl;

    @Value("${HEALTH_CHECK_URL}")
    private String heathCheckUrl;

    @Value("${HEALTH_CHECK2_URL}")
    private String heathCheck2Url;

    @Value("${TELEGRAM_BOT_MENTION}")
    @NotEmpty
    private String telegramBotMention;

    @Value("${ROLLBAR_ACCESS_KEY}")
    private String rollbarAccessKey;

    @Value("${ROLLBAR_ENVIRONMENT}")
    private String rollbarEnv;


    public String getTelegramKey() {
        return telegramKey;
    }

    public void setTelegramKey(String telegramKey) {
        this.telegramKey = telegramKey;
    }

    public String getLifecycleUrl() {
        return lifecycleUrl;
    }

    public void setLifecycleUrl(String lifecycleUrl) {
        this.lifecycleUrl = lifecycleUrl;
    }

    public String getHeathCheckUrl() {
        return heathCheckUrl;
    }

    public void setHeathCheckUrl(String heathCheckUrl) {
        this.heathCheckUrl = heathCheckUrl;
    }

    public String getHeathCheck2Url() {
        return heathCheck2Url;
    }

    public void setHeathCheck2Url(String heathCheck2Url) {
        this.heathCheck2Url = heathCheck2Url;
    }

    public String getTelegramBotMention() {
        return telegramBotMention;
    }

    public void setTelegramBotMention(String telegramBotMention) {
        this.telegramBotMention = telegramBotMention;
    }

    public String getRollbarAccessKey() {
        return rollbarAccessKey;
    }

    public void setRollbarAccessKey(String rollbarAccessKey) {
        this.rollbarAccessKey = rollbarAccessKey;
    }

    public String getRollbarEnv() {
        return rollbarEnv;
    }

    public void setRollbarEnv(String rollbarEnv) {
        this.rollbarEnv = rollbarEnv;
    }
}
