package com.practice.financialtracker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long walletId;
    @Column(name = "wallet_name")
    private String name;
    @Column(name = "wallet_amount")
    private double walletAmount;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Wallet(User user, WalletDto walletDto) {
        this.user = user;
        this.name = walletDto.getWalletName();
        this.walletAmount =walletDto.getWalletAmount();
    }
}
