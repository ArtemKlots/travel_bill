package com.travelBill.telegram.user;

import com.travelBill.api.core.event.Event;
import com.travelBill.api.core.event.EventService;
import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserService;
import com.travelBill.telegram.driver.ChatType;
import com.travelBill.telegram.driver.Request;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ActivityService {

    private final UserService userService;
    private final EventService eventService;

    public ActivityService(UserService userService,
                           EventService eventService) {
        this.userService = userService;
        this.eventService = eventService;
    }

    public void registerActivity(Request request) {
        if (request.chatType == ChatType.PRIVATE) {
            User user = request.user;
            user.setLastActivity(LocalDateTime.now());
            userService.save(user);
        } else {
            Event event = eventService.findByTelegramChatId(request.chatId);
            event.setLastActivity(LocalDateTime.now());
            eventService.save(event);
        }
    }
}
