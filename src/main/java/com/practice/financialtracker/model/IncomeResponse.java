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
    private String incomeCategory;
    private Double incomeAmount;
    private String description;
    private LocalDateTime date;
}
