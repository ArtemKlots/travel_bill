package com.travelBill.api.core.debt;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

//TODO Cover with tests
public interface DebtRepository extends JpaRepository<Debt, Long> {
    /**
     * Select and count all debts for user (grouped by currency and > 0)
     * The main query counts "How much do I owe", and the sub query counts "How mush somebody owed to me"
     */
    @Query(value = "SELECT currency, debtor_id as debtorId, user.first_name as userFirstName, user.last_name as userLastName, payer_id as payerId, " +
            "(SELECT sum(debt.amount) + IFNULL(sum(debt_subquery.amount) * -1, 0) FROM travel_bill.debt debt_subquery" +
            " where debt_subquery.payer_id = debt_subquery.payer_id " +
            "   and debt_subquery.debtor_id = :user_id " +
            "   and debt_subquery.currency = debt.currency) as amount " +
            " FROM travel_bill.debt debt" +
            " INNER JOIN user ON debt.debtor_id = user.id " +
            " WHERE payer_id = :user_id" +
            " GROUP BY debtor_id, currency",
            nativeQuery = true)
    List<DebtSumDto> getDebtsForUser(@Param("user_id") Long userId);
}
