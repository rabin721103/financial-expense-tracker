package com.practice.financialtracker.expensecategory;

import com.practice.financialtracker.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "expense_category")
public class ExpenseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseCategoryId;
    @Column(name = "expense_category_name", nullable = false)
    private String name;
    @Column(name = "expense_limit")
    private double expenseLimit;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    public ExpenseCategory(User user, ExpenseCategoryDto expenseCategoryDto) {
        this.user = user;
        this.name= expenseCategoryDto.getName();
        this.expenseLimit = expenseCategoryDto.getExpenseLimit();

    }
}
