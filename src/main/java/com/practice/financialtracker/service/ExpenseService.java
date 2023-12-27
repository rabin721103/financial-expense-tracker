package com.practice.financialtracker.service;

import com.practice.financialtracker.exceptions.CustomException;
import com.practice.financialtracker.expensecategory.ExpenseCategory;
import com.practice.financialtracker.expensecategory.ExpenseCategoryRepository;
import com.practice.financialtracker.model.*;
import com.practice.financialtracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public ExpenseService(ExpenseRepository expenseRepository, ExpenseCategoryRepository expenseCategoryRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public ExpenseDto getExpenseById(long id, long userId) {
        Optional<Expense> expense = expenseRepository.getExpenseById(id, userId);
        if (expense.isEmpty()) {
            throw new CustomException("Expense does not exist...");
        }
        Expense expense1 = expense.get();
        return new ExpenseDto(expense1.getExpenseName(), expense1.getExpenseCategory().getExpenseCategoryId(), expense1.getExpenseAmount(), expense1.getDescription());
    }

    public List<ExpenseResponse> getAllExpense(Long id) {
        List<Expense> expenses = expenseRepository.getExpenseByUserId(id);
        return expenses.stream().map(ExpenseResponse::new).toList();
    }

    public List<Expense> getExpenseByName(String name) {
        List<Expense> expenses = expenseRepository.findExpenseByName(name);
        return expenses.stream().map(expense -> new Expense(expense.getExpenseId(), expense.getExpenseName(), expense.getExpenseCategory(), expense.getExpenseAmount(), expense.getDescription(), expense.getDate(), expense.getUser())).toList();
    }

    public ExpenseResponse addExpense(Expense expense) {
        Long userId = expense.getUser().getUserId();
        Long categoryId = expense.getExpenseCategory().getExpenseCategoryId();
        ExpenseCategory expenseCategory = expenseCategoryRepository.findByExpenseCategoryId(categoryId);
        if (expenseCategory == null) {
            throw new CustomException("Expense category with ID " + categoryId + " not found.");
        }
        double maxLimit = expenseCategory.getExpenseLimit();
        Optional<Double> totalExpense = expenseRepository.getTotalExpenseByCategoryIdAndUserId(userId, categoryId);
        String categoryName = expenseCategory.getName();
        if (totalExpense.isPresent() && ((totalExpense.get() + expense.getExpenseAmount()) > maxLimit)) {
            throw new CustomException("Your Expense exceeds the maximum expense limit for " + categoryName);
        }
        Expense newExpense = expenseRepository.save(expense);
        return new ExpenseResponse(newExpense);
    }

    public void deleteExpenseById(Long expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if (expense.isPresent()) {
            expenseRepository.deleteById(expenseId);
        }
    }

    public ExpenseResponse updateExpense(Long id, ExpenseDto expenseDto) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            ExpenseCategory expenseCategory = new ExpenseCategory();
            expenseCategory.setExpenseCategoryId(expenseDto.getExpenseCategoryId());
            // Update properties of the existing income with values from the updated user
            existingExpense.setExpenseId(id);
            existingExpense.setExpenseName(expenseDto.getExpenseName());
            existingExpense.setExpenseCategory(expenseCategory);
            existingExpense.setExpenseAmount(expenseDto.getExpenseAmount());
            existingExpense.setDescription(expenseDto.getDescription());

            Expense updatedExpense = expenseRepository.save(existingExpense);
            return new ExpenseResponse(updatedExpense);

        } else {
            throw new CustomException("Income does not exist...");
        }
    }

    public List<ExpenseSummary> getData(Long id) {
        User user = new User();
        user.setUserId(id);
        List<Object[]> result = expenseRepository.getMonthlyExpenseSummaryByCategory(user);
        List<ExpenseSummary> dtos = new ArrayList<>();
        for (Object[] row : result) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            String category = (String) row[2];
            Double totalAmount = (Double) row[3];
            ExpenseSummary dto = new ExpenseSummary(year, month, category, totalAmount);
            dtos.add(dto);
        }
        return dtos;
    }

    public Double getTotalExpenseAmount(Long userId) {
        return expenseRepository.getTotalExpenseByUserId(userId);
    }

    public Optional<Double> getTotalCategoryAmount(Long userId, Long expenseCategoryId) {

        Optional<Double> expenseAmount = expenseRepository.getTotalExpenseByCategoryIdAndUserId(userId, expenseCategoryId);
        if (expenseAmount.isEmpty()) {
            return null;
        }
//        ExpenseCategory expenseCategory = expenseCategoryRepository.findByExpenseCategoryId(expenseCategoryId);

        return expenseAmount;
    }



}
