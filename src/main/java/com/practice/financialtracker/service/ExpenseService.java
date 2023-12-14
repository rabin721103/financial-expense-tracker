package com.practice.financialtracker.service;

import com.practice.financialtracker.exceptions.ExpenseNotFoundException;
import com.practice.financialtracker.model.Expense;
import com.practice.financialtracker.repository.ExpenseRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpenseService {
    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpense() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream().map(expense -> new Expense(expense.getExpenseId(), expense.getExpenseName(), expense.getExpenseCategory(), expense.getExpenseAmount(), expense.getDescription(), expense.getDate())).toList();
    }

    public Expense getExpenseById(Long id) throws ExpenseNotFoundException {
        Optional<Expense> expense = expenseRepository.findById(id);
        if (!expense.isPresent()) {
            throw new ExpenseNotFoundException("Expense does not exist...");
        }
        return expense.get();
    }

    public List<Expense> getExpenseByName(String name) {
        List<Expense> expenses = expenseRepository.findExpenseByName(name);
        return expenses.stream().map(expense -> new Expense(expense.getExpenseId(), expense.getExpenseName(), expense.getExpenseCategory(), expense.getExpenseAmount(), expense.getDescription(), expense.getDate())).toList();
    }

    public Expense addExpense(Expense expense) {


        expenseRepository.save(expense);
        return expense;
    }

    public void deleteExpenseById(Long expenseId) {
        Optional<Expense> expense = expenseRepository.findById(expenseId);

        if (expense.isPresent()) {
            expenseRepository.deleteById(expenseId);
        }
    }

    public ResponseEntity<Expense> updateExpense(Long id, Expense expense) throws ExpenseNotFoundException {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);

        if (optionalExpense.isPresent()) {
            Expense existingExpense = optionalExpense.get();
            // Update properties of the existing expense with values from the updated user
            existingExpense.setExpenseId(id);
            existingExpense.setExpenseName(expense.getExpenseName());
            existingExpense.setExpenseCategory(expense.getExpenseCategory());
            existingExpense.setExpenseAmount(expense.getExpenseAmount());
            existingExpense.setDate(expense.getDate());
            existingExpense.setDescription(expense.getDescription());

            Expense newExpense = expenseRepository.save(existingExpense);
            return ResponseEntity.ok(newExpense);

        } else {
            throw new ExpenseNotFoundException("Expense does not exist...");
        }

    }
}
