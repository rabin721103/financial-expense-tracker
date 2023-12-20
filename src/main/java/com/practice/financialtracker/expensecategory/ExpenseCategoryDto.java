package com.practice.financialtracker.expensecategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseCategoryDto {
    private String name;
    private double expenseLimit;
}
