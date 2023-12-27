package com.practice.financialtracker.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IncomeSummary {
    private Integer year;
    private Integer month;
    private String incomeCategoryName;
    private Double totalAmount;
}
