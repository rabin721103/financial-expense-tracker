package com.practice.financialtracker.service;

import com.practice.financialtracker.model.*;
import com.practice.financialtracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }
    public List<IncomeResponse> getAllIncome() {
        List<Income> incomes = incomeRepository.findAll();
        return incomes.stream().map(income -> new IncomeResponse(income.getIncomeId(), income.getIncomeName(), income.getIncomeCategory(), income.getIncomeAmount(), income.getDescription(), income.getDate())).toList();
    }

    public IncomeDto addIncome(Income newIncome) {
        Income savedIncome = incomeRepository.save(newIncome);
        return new IncomeDto(savedIncome.getIncomeName(), savedIncome.getIncomeCategory(),savedIncome.getIncomeAmount(),savedIncome.getDescription());
    }
    public void deleteIncome(Long incomeId) {
        Optional<Income> income = incomeRepository.findById(incomeId);

        if (income.isPresent()) {
            incomeRepository.deleteById(incomeId);
        }
    }
}
