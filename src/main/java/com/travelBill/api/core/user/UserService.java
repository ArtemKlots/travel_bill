package com.travelBill.api.core.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByTelegramId(Integer id) {
        return userRepository.findUserByTelegramId(id);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}