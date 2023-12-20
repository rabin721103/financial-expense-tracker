package com.practice.financialtracker.model;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    @Column
    private String expenseName;
    @ManyToOne
    @JoinColumn(name = "expense_category_id", nullable = false)
    private ExpenseCategory expenseCategory;
    @Column
    private Double expenseAmount;
    @Column
    private String description;
    @Column
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Expense(User user, ExpenseDto expenseDto) {
        this.user = user;
        this.expenseName = expenseDto.getExpenseName();
        this.expenseAmount = expenseDto.getExpenseAmount();
        ExpenseCategory newExpenseCategory = new ExpenseCategory();
        newExpenseCategory.setExpenseCategoryId(expenseDto.getExpenseCategoryId());
        this.expenseCategory = newExpenseCategory;
        this.description = expenseDto.getDescription();

    }
}
