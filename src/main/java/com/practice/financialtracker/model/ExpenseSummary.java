package com.practice.financialtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseSummary {
    private Integer year;
    private Integer month;
    private String expenseCategoryName;
    private Double totalAmount;
}
