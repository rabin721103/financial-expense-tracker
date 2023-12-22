package com.practice.financialtracker.repository;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import com.practice.financialtracker.model.Income;
import com.practice.financialtracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IncomeRepository  extends JpaRepository<Income, Long> {


}
