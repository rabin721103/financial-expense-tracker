package com.practice.financialtracker.repository;

import com.practice.financialtracker.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {

}
