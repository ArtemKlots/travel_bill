package com.travelBill.telegram.scenario;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KeyboardVersionStorage {
    public LocalDateTime getPrivateKeyboardReleaseDate() {
        return LocalDateTime.of(2020, 9, 30, 23, 59);
    }

    public LocalDateTime getGroupKeyboardReleaseDate() {
        return LocalDateTime.of(2020, 9, 30, 23, 59);
    }
}
