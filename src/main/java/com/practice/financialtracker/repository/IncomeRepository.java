package com.practice.financialtracker.repository;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import com.practice.financialtracker.model.Expense;
import com.practice.financialtracker.model.Income;
import com.practice.financialtracker.model.IncomeDto;
import com.practice.financialtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IncomeRepository  extends JpaRepository<Income, Long> {
    @Query(value = "select * from income where user_id = :userId", nativeQuery = true)
    List<Income> getIncomeByUserId(Long userId);

    @Query(value = "select * from income where income_id = :incomeId and user_id =:userId", nativeQuery = true)
    Optional<Income> getIncomeById(long incomeId, long userId);

    @Query(value = "SELECT YEAR(i.date) as year, MONTH(i.date) as month, i.incomeCategory.categoryName as category, SUM(i.incomeAmount) as totalAmount " +
            "FROM Income i " +
            "WHERE i.user = :user " +
            "GROUP BY YEAR(i.date), MONTH(i.date), i.incomeCategory.categoryName " +
            "ORDER BY YEAR(i.date) DESC, MONTH(i.date) DESC, i.incomeCategory.categoryName")
    List<Object[]> getMonthlyIncomeSummaryByCategory(User user);

    @Query(value = "SELECT SUM(income_amount) FROM income WHERE user_id = :userId", nativeQuery = true)
    Double getTotalIncomeByUserId(Long userId);


}
