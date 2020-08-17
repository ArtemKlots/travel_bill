package com.travelBill.telegram.user.state;

import org.springframework.stereotype.Service;

//TODO cover with tests
@Service
public class UserStateService {
    private final UserStateRepository userStateRepository;

    public UserStateService(UserStateRepository userStateRepository) {
        this.userStateRepository = userStateRepository;
    }

    public UserState get(Long userId) {
        return userStateRepository.getOne(userId);
    }

    public void change(UserState state) {
        if (state.getUser() == null || state.getState() == null) {
            throw new IllegalArgumentException();
        }

        userStateRepository.save(state);
    }
}
