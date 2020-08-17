package com.travelBill.telegram.user;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.user.state.UserState;
import com.travelBill.telegram.user.state.UserStateService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.travelBill.telegram.user.state.State.START;

@Service
@Transactional
public class TelegramUserService {
    private final UserService userService;
    private final UserStateService userStateService;

    public TelegramUserService(UserService userService, UserStateService userStateService) {
        this.userService = userService;
        this.userStateService = userStateService;
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
        newUser.setLastActivity(LocalDateTime.now());

        User createdUser = userService.save(newUser);
        userStateService.change(new UserState(newUser, START));
        return createdUser;
    }
}
