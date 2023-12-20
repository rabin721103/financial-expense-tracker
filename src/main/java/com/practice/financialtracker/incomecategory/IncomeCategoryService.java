package com.practice.financialtracker.incomecategory;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeCategoryService {
    private final IncomeCategoryRepository incomeCategoryRepository;

    public IncomeCategoryService(IncomeCategoryRepository incomeCategoryRepository) {
        this.incomeCategoryRepository = incomeCategoryRepository;
    }

    public List<IncomeCategoryResponse> getAllIncomeCategory() {
        List<IncomeCategory> incomeCategories = incomeCategoryRepository.findAll();
        return incomeCategories.stream().map(incomeCategory -> new IncomeCategoryResponse( incomeCategory.getIncomeCategoryId(),incomeCategory.getCategoryName())).toList();
    }
    public IncomeCategoryDto addIncomeCategory(IncomeCategory newIncomeCategory) {
        IncomeCategory category = incomeCategoryRepository.save(newIncomeCategory);
        return new IncomeCategoryDto(category.getCategoryName());
    }
}
