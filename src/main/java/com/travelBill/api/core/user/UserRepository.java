package com.travelBill.api.core.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByTelegramId(Integer id);

    @Query(value = "" +
            "select * from user where id != :user_id and (id in ( " +
            "  select distinct user_id  " +
            "  from event_user  " +
            "  where event_id in ( " +
            "    select event_id  " +
            "    from event_user  " +
            "    where user_id = :user_id" +
            "  ) " +
            ")) order by user.first_name, user.last_name",
            nativeQuery = true)
    List<User> getAllContactedUsers(@Param("user_id") Long userId);
}
