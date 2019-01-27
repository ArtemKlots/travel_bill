package com.travelBill.config;

import static org.springframework.util.StringUtils.isEmpty;

public class ApplicationConfiguration {

    public static String getTelegramApiKey() {
        return getEnvironmentVariable("TELEGRAM_KEY");
    }

    public static String getDbUser() {
        return getEnvironmentVariable("DB_USER");
    }

    public static String getDbPassword() {
        return getEnvironmentVariable("DB_PASSWORD");
    }

    public static String getDbUrl() {
        return getEnvironmentVariable("DB_URL");
    }

    private static String getEnvironmentVariable(String name) {
        String key = System.getenv(name);
        if (isEmpty(key)) {
            throw new WrongApplicationConfigurationException(name + " wasn't provided");
        } else {
            return key;
        }
    }
}
