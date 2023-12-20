package com.practice.financialtracker.incomecategory;

import com.practice.financialtracker.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "income_category")
public class IncomeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeCategoryId;
    @Column
    private String categoryName;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public IncomeCategory(User user, IncomeCategoryDto incomeCategoryDto) {
        this.user= user;
        this.categoryName = incomeCategoryDto.getCategoryName();
    }
}
