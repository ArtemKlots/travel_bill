package com.travelBill.api.user;

import com.travelBill.api.core.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByTelegramId(Integer id);
}
