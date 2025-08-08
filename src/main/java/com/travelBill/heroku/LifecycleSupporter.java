package com.travelBill.heroku;

import com.travelBill.RollbarLogger;
import com.travelBill.config.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

@Service
public class LifecycleSupporter {
    private final ApplicationConfiguration applicationConfiguration;
    private final RollbarLogger rollbarLogger;

    @Autowired
    public LifecycleSupporter(ApplicationConfiguration applicationConfiguration, RollbarLogger rollbarLogger) {
        this.applicationConfiguration = applicationConfiguration;
        this.rollbarLogger = rollbarLogger;
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void resetLifecycle() {
        String botMode = applicationConfiguration.getBotMode();
        if (Objects.equals(botMode, "WEBHOOK")) return;

        try {
            URL url = new URL(applicationConfiguration.getLifecycleUrl());
            URLConnection connection = url.openConnection();
            connection.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 90 * 60 * 1000)
    public void reportHealth() {
        try {
            URL url = new URL(applicationConfiguration.getHeathCheckUrl());
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void reportHealth2() {
        try {
            String requestUrl = applicationConfiguration.getHeathCheck2Url();
            if (requestUrl != null && requestUrl.length() != 0) {
                URL url = new URL(requestUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                InputStream is = connection.getInputStream();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000)
    public void reportLogs() {
        try {
            rollbarLogger.log("Rollbar lifecycle log");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
