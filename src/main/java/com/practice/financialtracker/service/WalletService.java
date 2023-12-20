package com.practice.financialtracker.service;

import com.practice.financialtracker.model.*;
import com.practice.financialtracker.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletService {
    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }
    public List<WalletResponse> getAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        return wallets.stream().map(wallet -> new WalletResponse(wallet.getWalletId(),wallet.getName(),wallet.getWalletAmount())).toList();
    }
    public WalletDto addWallet(Wallet newWallet) {
        Wallet savedWallet = walletRepository.save(newWallet);
        return new WalletDto(savedWallet.getName(), savedWallet.getWalletAmount());
    }


}
