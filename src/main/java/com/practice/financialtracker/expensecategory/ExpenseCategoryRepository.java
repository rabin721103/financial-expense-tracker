package com.practice.financialtracker.expensecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, Long> {

    @Query(value = "SELECT * FROM expense_category WHERE expense_category_name = :name", nativeQuery = true)
    ExpenseCategory findExpenseCategoryByName(String name);
}
