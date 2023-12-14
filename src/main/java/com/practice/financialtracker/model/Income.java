package com.practice.financialtracker.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long incomeId;
    @Column
    private String incomeCategory;
    @Column
    private Double incomeAmount;
    @Column
    private String remarks;
    @Column
    private LocalDateTime date = LocalDateTime.now();
}
