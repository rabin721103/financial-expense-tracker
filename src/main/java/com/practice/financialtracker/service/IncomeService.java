package com.practice.financialtracker.service;

import com.practice.financialtracker.exceptions.CustomException;
import com.practice.financialtracker.exceptions.ExpenseNotFoundException;
import com.practice.financialtracker.incomecategory.IncomeCategory;
import com.practice.financialtracker.model.*;
import com.practice.financialtracker.repository.IncomeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public IncomeResponse updateIncome(Long id, IncomeDto income) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);

        if (optionalIncome.isPresent()) {
            Income existingIncome = optionalIncome.get();
            IncomeCategory incomeCategory = new IncomeCategory();
            incomeCategory.setIncomeCategoryId(income.getIncomeCategoryId());
            // Update properties of the existing income with values from the updated user
            existingIncome.setIncomeId(id);
            existingIncome.setIncomeName(income.getIncomeName());
            existingIncome.setIncomeCategory(incomeCategory);
            existingIncome.setIncomeAmount(income.getIncomeAmount());
            existingIncome.setDescription(income.getDescription());

            Income updatedIncome = incomeRepository.save(existingIncome);
            return new IncomeResponse(updatedIncome);

        } else {
            throw new CustomException("Income does not exist...");
        }
    }

    public List<IncomeSummary> getDataByCategory(Long id){
        User user = new User();
        user.setUserId(id);
        List<Object[]> result = incomeRepository.getMonthlyIncomeSummaryByCategory(user);
        List<IncomeSummary> dtos = new ArrayList<>();
        for (Object[] row : result) {
            Integer year = (Integer) row[0];
            Integer month = (Integer) row[1];
            String category = (String) row[2];
            Double totalAmount = (Double) row[3];

            IncomeSummary dto = new IncomeSummary(year, month, category, totalAmount);
            dtos.add(dto);
        }
        return dtos;
    }

    public Double getTotalIncomeAmount(Long userId){
        return incomeRepository.getTotalIncomeByUserId(userId);
    }

}
