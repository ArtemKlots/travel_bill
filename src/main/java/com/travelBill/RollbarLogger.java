package com.travelBill;

import com.rollbar.notifier.Rollbar;
import com.rollbar.notifier.config.Config;
import com.rollbar.notifier.config.ConfigBuilder;
import com.travelBill.config.ApplicationConfiguration;
import org.springframework.stereotype.Service;

@Service
public class RollbarLogger {

    Rollbar rollbar;

    RollbarLogger(ApplicationConfiguration applicationConfiguration) {
        try {
            if (applicationConfiguration.getRollbarAccessKey().length() == 0) return;
            Config config = ConfigBuilder.withAccessToken(applicationConfiguration.getRollbarAccessKey())
                    .environment(applicationConfiguration.getRollbarEnv())
                    .build();
            rollbar = Rollbar.init(config);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void warn(Exception e, String message) {
        if (rollbar != null) {
            rollbar.warning(e, message);
        }
    }

    public void error(Exception e, String message) {
        if (rollbar != null) {
            rollbar.error(e, message);
        }
    }

    public void error(Exception e) {
        if (rollbar != null) {
            rollbar.error(e);
        }
    }
}
