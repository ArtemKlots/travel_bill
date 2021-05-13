package com.travelBill.web;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.travelBill.TravelBillException;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.config.ApplicationConfiguration;
import com.travelBill.splitBill.core.AccessDeniedException;
import com.travelBill.telegram.RollbarLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static com.travelBill.web.LoginValidator.validate;
import static java.util.Objects.nonNull;

@RestController()
public class LoginController {
    private UserService userService;
    private ApplicationConfiguration applicationConfiguration;
    private RollbarLogger rollbarLogger;

    @PostMapping(value = "login")
    public String login(@RequestBody TelegramLoginDto telegramLoginDto) {
        boolean isAuthValid = false;
        try {
            isAuthValid = validate(telegramLoginDto, applicationConfiguration.getTelegramKey());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            rollbarLogger.error(e);
            throw new TravelBillException("Login is temporarily unavailable");
        }

        if (!isAuthValid) {
            rollbarLogger.warn(null, "Attempt to use incorrect telegram credentials!" + telegramLoginDto.toString());
            throw new AccessDeniedException();
        }

        User user = userService.findUserByTelegramId(telegramLoginDto.getId());

        if (nonNull(telegramLoginDto.getPhotoUrl()) && !Objects.equals(user.getPhotoUrl(), telegramLoginDto.getPhotoUrl())) {
            user.setPhotoUrl(telegramLoginDto.getPhotoUrl());
            user = userService.save(user);
        }

        Algorithm algorithm = Algorithm.HMAC256(applicationConfiguration.getJwtSecret());
        return JWT.create()
                .withIssuer("TravelBill")
                .withClaim("userId", user.getId())
                .withClaim("userFullName", user.getFullName())
                .sign(algorithm);
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Autowired
    public void setRollbarLogger(RollbarLogger rollbarLogger) {
        this.rollbarLogger = rollbarLogger;
    }

}
