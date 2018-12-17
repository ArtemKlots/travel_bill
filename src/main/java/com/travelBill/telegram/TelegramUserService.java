package com.travelBill.telegram;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import org.springframework.stereotype.Service;

@Service
public class TelegramUserService {
    private final UserService userService;

    public TelegramUserService(UserService userService) {
        this.userService = userService;
    }

    public User setupUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User user = userService.findUserByTelegramId(telegramUser.getId());
        if (user == null) {
            user = createFromTelegramUser(telegramUser);
        }

        return user;
    }

    public User createFromTelegramUser(org.telegram.telegrambots.meta.api.objects.User telegramUser) {
        User newUser = new User();
        newUser.setTelegramId(telegramUser.getId());
        newUser.setFirstName(telegramUser.getFirstName());
        newUser.setLastName(telegramUser.getLastName());
        return userService.save(newUser);
    }
}
