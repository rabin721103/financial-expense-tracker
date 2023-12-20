package com.practice.financialtracker.model;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseResponse {
    private Long expenseId;
    private String expenseName;
    private Long expenseCategoryId;
    private String expenseCategoryName;
    private Double expenseAmount;
    private String description;
    private LocalDateTime date;


  public ExpenseResponse(Expense expense) {
        this.expenseId = expense.getExpenseId();
        this.expenseName = expense.getExpenseName();
        this.expenseAmount = expense.getExpenseAmount();
        this.description = expense.getDescription();
        this.date = expense.getDate();
        this.expenseCategoryId = expense.getExpenseCategory().getExpenseCategoryId();
        this.expenseCategoryName = expense.getExpenseCategory().getName();
    }
}
