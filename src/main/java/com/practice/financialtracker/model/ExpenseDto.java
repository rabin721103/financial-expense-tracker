package com.practice.financialtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseDto {
    private String expenseName;
    private String expenseCategory;
    private Double expenseAmount;
    private String description;
}
