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
            ") or id in (:included_ids)) limit :limit",
            nativeQuery = true)
    List<User> getLastContactedUsersAnd(@Param("user_id") Long userId, @Param("included_ids") List<Long> includedIds, @Param("limit") int limit);
}
