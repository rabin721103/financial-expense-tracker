package com.practice.financialtracker.incomecategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IncomeCategoryResponse {
    private Long incomeCategoryId;
    private String categoryName;
}
