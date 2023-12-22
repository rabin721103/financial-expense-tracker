package com.practice.financialtracker.incomecategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IncomeCategoryRepository extends JpaRepository<IncomeCategory,Long> {
    @Query(value = "SELECT * FROM income_category WHERE income_category_name = :categoryName", nativeQuery = true)
    IncomeCategory findIncomeCategoryByName(String categoryName);

    List<IncomeCategory> findIncomeCategoriesByUserUserId(Long userId);

}
