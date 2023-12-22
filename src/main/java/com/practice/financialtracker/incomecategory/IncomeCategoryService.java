package com.practice.financialtracker.incomecategory;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
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
    public IncomeCategory findIncomeCategoryByName(String name) {
        return incomeCategoryRepository.findIncomeCategoryByName(name);
    }
    public List<IncomeCategoryResponse> getAllIncomeCategoriesByUserId(Long userId) {
        // Assuming you have a method in your repository to find by user ID
        List<IncomeCategory> incomeCategories = incomeCategoryRepository.findIncomeCategoriesByUserUserId(userId);
        return incomeCategories.stream().map(incomeCategory -> new IncomeCategoryResponse(
                        incomeCategory.getIncomeCategoryId(), incomeCategory.getCategoryName()))
                .toList();
    }

    public IncomeCategoryDto addIncomeCategory(IncomeCategory newIncomeCategory) {
        IncomeCategory category = incomeCategoryRepository.save(newIncomeCategory);
        return new IncomeCategoryDto(category.getCategoryName());
    }
}
