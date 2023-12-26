package com.practice.financialtracker.service;

import com.practice.financialtracker.exceptions.CustomException;
import com.practice.financialtracker.exceptions.ExpenseNotFoundException;
import com.practice.financialtracker.model.*;
import com.practice.financialtracker.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {
    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }


    public List<IncomeResponse> getAllIncomes(Long id) {
        List<Income> incomes = incomeRepository.getIncomeByUserId(id);
        return incomes.stream().map(IncomeResponse::new).toList();
    }

    public IncomeDto getIncomeById(long id, long userId) {
        Optional<Income> income = incomeRepository.getIncomeById(id, userId);
        if (income.isEmpty()) {
            throw new CustomException("Income does not exist...");
        }
        Income income1 = income.get();
        return new IncomeDto(income1.getIncomeName(), income1.getIncomeCategory().getIncomeCategoryId(),income1.getIncomeAmount(),income1.getDescription());
    }

    public IncomeResponse addIncome(Income newIncome) {
        Income savedIncome = incomeRepository.save(newIncome);
        return new IncomeResponse(savedIncome);
    }

    public void deleteIncome(Long incomeId) {
        Optional<Income> income = incomeRepository.findById(incomeId);

        if (income.isPresent()) {
            incomeRepository.deleteById(incomeId);
        }
    }
//    public ResponseEntity<Income> updateIncome(Long id, Income income){
//        Optional<Income> optionalIncome = incomeRepository.findById(id);
//
//        if (optionalIncome.isPresent()) {
//            Income existingIncome = optionalIncome.get();
//            // Update properties of the existing expense with values from the updated user
//            existingIncome.setIncomeId(id);
//            existingIncome.setIncomeName(income.getIncomeName());
//            existingIncome.setIncomeCategory(income.getIncomeCategory());
//            existingIncome.setIncomeAmount(income.getIncomeAmount());
//            existingIncome.setDate(income.getDate());
//            existingIncome.setDescription(income.getDescription());
//
//            Income newIncome = incomeRepository.save(existingIncome);
//            return ResponseEntity.ok(newIncome);
//
//        } else {
//            throw new CustomException("Income does not exist...");
//        }
//
//    }

    public IncomeResponse updateIncome(Long id, Income income) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);

        if (optionalIncome.isPresent()) {
            Income existingIncome = optionalIncome.get();
            // Update properties of the existing income with values from the updated user
            existingIncome.setIncomeId(id);
            existingIncome.setIncomeName(income.getIncomeName());
            existingIncome.setIncomeCategory(income.getIncomeCategory());
            existingIncome.setIncomeAmount(income.getIncomeAmount());
            existingIncome.setDate(income.getDate());
            existingIncome.setDescription(income.getDescription());

            Income updatedIncome = incomeRepository.save(existingIncome);
            return new IncomeResponse(updatedIncome);

        } else {
            throw new CustomException("Income does not exist...");
        }
    }

}
