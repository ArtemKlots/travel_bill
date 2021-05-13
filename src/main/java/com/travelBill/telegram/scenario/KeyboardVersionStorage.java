package com.travelBill.telegram.scenario;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KeyboardVersionStorage {
    public LocalDateTime getPrivateKeyboardReleaseDate() {
        return LocalDateTime.of(2021, 5, 13, 20, 0);
    }

    public LocalDateTime getGroupKeyboardReleaseDate() {
        return LocalDateTime.of(2020, 9, 30, 23, 59);
    }
}
