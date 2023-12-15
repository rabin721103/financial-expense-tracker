package com.practice.financialtracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column
    private String userName;
    @Column
    private String password;
    @Column
    private String email;
    @Column
    private String profession;
}
