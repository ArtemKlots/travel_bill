package com.travelBill.splitBill.core.user;

import com.travelBill.api.core.user.User;
import com.travelBill.api.core.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SbUserService {
    private final UserRepository userRepository;
    private final SbUserRepository sbUserRepository;

    public SbUserService(UserRepository userRepository, SbUserRepository sbUserRepository) {
        this.userRepository = userRepository;
        this.sbUserRepository = sbUserRepository;
    }

    public List<User> getAllContactedUsers(Long userId) {
        List<User> travelBillEventUsers = userRepository.getAllContactedUsers(userId);
        List<User> splitBillUsers = sbUserRepository.getAllContactedUsers(userId);
        Set<User> set = new LinkedHashSet<>(travelBillEventUsers);
        set.addAll(splitBillUsers);
        return new ArrayList<>(set);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
