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
public class IncomeResponse {
    private Long incomeId;
    private String incomeName;
    private Long incomeCategoryId;
    private String incomeCategoryName;
    private Double incomeAmount;
    private String description;
    private LocalDateTime date;

    public IncomeResponse(Income income) {
        this.incomeId = income.getIncomeId();
        this.incomeName = income.getIncomeName();
        this.incomeAmount = income.getIncomeAmount();
        this.description = income.getDescription();
        this.date = income.getDate();
        this.incomeCategoryId = income.getIncomeCategory().getIncomeCategoryId();
        this.incomeCategoryName = income.getIncomeCategory().getCategoryName();
    }
}
