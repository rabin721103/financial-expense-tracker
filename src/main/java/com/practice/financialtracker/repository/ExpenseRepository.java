package com.practice.financialtracker.repository;

import com.practice.financialtracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {

    @Query("SELECT c FROM Expense c WHERE c.expenseName = ?1")
    List<Expense> findExpenseByName(String expenseName);


}
