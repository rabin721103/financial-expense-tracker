package com.practice.financialtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeDto {
    private String incomeName;
    private String incomeCategory;
    private Double incomeAmount;
    private String description;
}
