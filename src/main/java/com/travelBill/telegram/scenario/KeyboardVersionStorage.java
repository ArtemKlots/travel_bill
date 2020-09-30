package com.travelBill.telegram.scenario;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KeyboardVersionStorage {
    public LocalDateTime getPrivateKeyboardReleaseDate() {
        return LocalDateTime.of(2020, 10, 1, 12, 0);
    }

    public LocalDateTime getGroupKeyboardReleaseDate() {
        return LocalDateTime.of(2020, 10, 1, 12, 0);
    }
}
