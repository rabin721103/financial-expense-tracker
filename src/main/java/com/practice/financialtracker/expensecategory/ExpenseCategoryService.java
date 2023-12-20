package com.practice.financialtracker.expensecategory;

import com.practice.financialtracker.model.Income;
import com.practice.financialtracker.model.IncomeDto;
import com.practice.financialtracker.model.IncomeResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseCategoryService {
    private final ExpenseCategoryRepository expenseCategoryRepository;


    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public List<ExpenseCategoryResponse> getAllExpenseCategory() {
        List<ExpenseCategory> expenseCategories = expenseCategoryRepository.findAll();
        return expenseCategories.stream().map(expenseCategory -> new ExpenseCategoryResponse( expenseCategory.getExpenseCategoryId(),expenseCategory.getName(),expenseCategory.getExpenseLimit())).toList();
    }
    public ExpenseCategoryDto addExpenseCategory(ExpenseCategory newExpenseCategory) {
        ExpenseCategory category = expenseCategoryRepository.save(newExpenseCategory);
        return new ExpenseCategoryDto(category.getName(),category.getExpenseLimit());
    }
}
