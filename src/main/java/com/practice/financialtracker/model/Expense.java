package com.practice.financialtracker.model;

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
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;
    @Column
    private String expenseName;
    @Column
    private String expenseCategory;
    @Column
    private Double expenseAmount;
    @Column
    private String description;
    @Column
    private LocalDateTime date = LocalDateTime.now();

}
