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


}
