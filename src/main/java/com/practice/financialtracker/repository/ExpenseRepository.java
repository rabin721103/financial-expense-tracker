package com.practice.financialtracker.repository;

import com.practice.financialtracker.model.Expense;
import com.practice.financialtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT c FROM Expense c WHERE c.expenseName = ?1")
    List<Expense> findExpenseByName(String expenseName);

    @Query(value = "select * from expenses where user_id = :userId", nativeQuery = true)
    List<Expense> getExpenseByUserId(Long userId);

    @Query(value = "select * from expenses where expense_id = :expenseId and user_id =:userId", nativeQuery = true)
    Optional<Expense> getExpenseById(long expenseId, long userId);

    @Query(value = "select * from expenses where user_id = :userId and expense_category_id = :expenseCategoryId", nativeQuery = true)
    List<Expense> getExpenseByUserIdAndCategoryId(Long userId, Long expenseCategoryId);

    @Query(value = "SELECT SUM(e.expense_amount) FROM expenses e WHERE e.user_id = :userId and e.expense_category_id = :expenseCategoryId", nativeQuery = true)
    Optional<Double> getTotalExpenseByCategoryIdAndUserId(Long userId,Long expenseCategoryId);

    @Query(value = "SELECT YEAR(e.date) as year, MONTH(e.date) as month, e.expenseCategory.name as category, SUM(e.expenseAmount) as totalAmount " +
            "FROM Expense e " +
            "WHERE e.user = :user " +
            "GROUP BY YEAR(e.date), MONTH(e.date), e.expenseCategory.name " +
            "ORDER BY YEAR(e.date) DESC, MONTH(e.date) DESC, e.expenseCategory.name")
    List<Object[]> getMonthlyExpenseSummaryByCategory(User user);

    @Query(value = "SELECT SUM(expense_amount) FROM expenses WHERE user_id = :userId", nativeQuery = true)
    Double getTotalExpenseByUserId(Long userId);



}
