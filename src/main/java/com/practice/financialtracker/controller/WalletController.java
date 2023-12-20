package com.practice.financialtracker.controller;

import com.practice.financialtracker.model.*;
import com.practice.financialtracker.service.WalletService;
import com.practice.financialtracker.utils.ResponseWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }
    @GetMapping("/allWallets")
    public ResponseEntity<ResponseWrapper<List<WalletResponse>>> getAllWallets() {
        ResponseWrapper<List<WalletResponse>> response = new ResponseWrapper<>();
        try {
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("Wallet retrieved successfully");
            response.setResponse( walletService.getAllWallets());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setSuccess(false);
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping()
    public ResponseEntity<ResponseWrapper<WalletDto>> addWallet(HttpServletRequest request, @RequestBody WalletDto walletDto){
        ResponseWrapper<WalletDto> response = new ResponseWrapper<>();
        try{
            Long decodedUserId = (Long) request.getAttribute("userId");
            User user = new User();
            user.setUserId(decodedUserId);
            Wallet newWallet = new Wallet(user, walletDto);
            response.setStatusCode(HttpStatus.OK.value());
            response.setSuccess(true);
            response.setMessage("expense added successfully");
            response.setResponse(walletService.addWallet(newWallet));
            return ResponseEntity.ok(response);
        }
        catch(Exception e) {
            response.setStatusCode(HttpStatus.NOT_FOUND.value());
            response.setMessage("Internal Server Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}
