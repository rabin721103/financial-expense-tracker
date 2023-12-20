package com.practice.financialtracker.repository;

import com.practice.financialtracker.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository  extends JpaRepository<Income, Long> {
}
