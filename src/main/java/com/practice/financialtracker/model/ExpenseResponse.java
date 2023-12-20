package com.practice.financialtracker.model;

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
    private String expenseCategory;
    private Double expenseAmount;
    private String description;
    private LocalDateTime date;
}
