package com.travelBill.splitBill.core.user;

import com.travelBill.api.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SbUserRepository extends JpaRepository<User, Long> {

    @Query(value = "" +
            "select * from user where id != :user_id and (id in ( " +
            "  select distinct user_id  " +
            "  from sb_bill_user  " +
            "  where bill_id in ( " +
            "    select bill_id  " +
            "    from sb_bill_user  " +
            "    where user_id = :user_id" +
            "  ) " +
            ")) order by user.first_name, user.last_name",
            nativeQuery = true)
    List<User> getAllContactedUsers(@Param("user_id") Long userId);
}
