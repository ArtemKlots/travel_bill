package com.travelBill.api.core.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    @Query(value = "" +
            "select :user_id payerId, user_id debtorId, sum(money) amount, currency, user.first_name as userFirstName, user.last_name as userLastName from " +
            "  (select payer_id user_id, sum(amount)*-1 money, currency" +
            "  from debt" +
            "  where debtor_id=:user_id" +
            "  group by payer_id, currency" +
            "  union all" +
            "    select debtor_id user_id, sum(amount) money, currency" +
            "  from debt " +
            "  where payer_id=:user_id" +
            "  group by debtor_id, currency) as subquery" +
            "  INNER JOIN user ON user_id = user.id " +
            "  WHERE user_id = user.id" +
            "  group by user_id, currency" +
            "  having sum(money) != 0;",
            nativeQuery = true)
    List<DebtSumDto> getDebtsForUser(@Param("user_id") Long userId);
}
