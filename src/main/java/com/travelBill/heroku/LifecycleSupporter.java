package com.travelBill.heroku;

import com.travelBill.config.ApplicationConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@Service
public class LifecycleSupporter {
    private final ApplicationConfiguration applicationConfiguration;

    @Autowired
    public LifecycleSupporter(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Scheduled(fixedRate = 10 * 60 * 1000)
    public void resetLifecycle() {
        try {
            URL url = new URL(applicationConfiguration.getLifecycleUrl());
            URLConnection connection = url.openConnection();
            InputStream is = connection.getInputStream();
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


}
