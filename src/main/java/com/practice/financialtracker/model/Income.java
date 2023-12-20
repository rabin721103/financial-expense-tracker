package com.practice.financialtracker.model;

import com.practice.financialtracker.expensecategory.ExpenseCategory;
import com.practice.financialtracker.incomecategory.IncomeCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;
    @Column
    private String incomeName;
    @ManyToOne
    @JoinColumn(name = "income_category_id", nullable = false)
    private IncomeCategory incomeCategory;
    @Column
    private Double incomeAmount;
    @Column
    private String description;
    @Column
    private LocalDateTime date = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)

    private User user;


    public Income(User user, IncomeDto incomeDto) {
        this.user = user;
        this.incomeName = incomeDto.getIncomeName();
        this.incomeAmount = incomeDto.getIncomeAmount();
        IncomeCategory newIncomeCategory = new IncomeCategory();
        newIncomeCategory.setIncomeCategoryId(incomeDto.getIncomeCategoryId());
        this.incomeCategory = newIncomeCategory;
        this.description = incomeDto.getDescription();

    }
}
