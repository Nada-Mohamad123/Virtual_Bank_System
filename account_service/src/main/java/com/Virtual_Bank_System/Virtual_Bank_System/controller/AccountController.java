package com.Virtual_Bank_System.Virtual_Bank_System.controller;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.*;
import com.Virtual_Bank_System.Virtual_Bank_System.model.Account;
import com.Virtual_Bank_System.Virtual_Bank_System.service.AccountService;
import com.Virtual_Bank_System.Virtual_Bank_System.service.LogProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private final AccountService accountService;
    private final LogProducerService logProducer;
    public AccountController(AccountService accountService, LogProducerService logProducer){
        this.accountService = accountService;
        this.logProducer = logProducer;
    }

    // Create new account and return DTO response
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO dto) {
        logProducer.sendLog(dto, "Request");
        try {
            Account savedAccount = accountService.createAccount(dto);

            AccountResponseDTO response = new AccountResponseDTO(
                    savedAccount.getId().toString(),
                    savedAccount.getAccountNumber(),
                    "Account created successfully."
            );

            logProducer.sendLog(response, "Response");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logProducer.sendLog("Failed to create account: " + e.getMessage(), "Error");
            throw e;
        }
    }
@GetMapping("/{accountId}")

public ResponseEntity<AccountDetailsDTO> getAccountById(@PathVariable UUID accountId) {
    logProducer.sendLog("GET /accounts/ : " + accountId, "Request");
    AccountDetailsDTO details = accountService.getAccountById(accountId);
    logProducer.sendLog("Fetched account details for ID: " + accountId, "Success");
    return ResponseEntity.ok(details);
}

   //  Transfer funds using UUID IDs
   @PutMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequestDTO dto) {
       logProducer.sendLog(dto, "Request");

       try {
           accountService.transferFunds(dto);

           TransferResponseDTO response = new TransferResponseDTO("Transfer completed successfully.");
           logProducer.sendLog(response, "Response");

           return ResponseEntity.ok(response);

       } catch (Exception e) {
           logProducer.sendLog("Failed to transfer funds: " + e.getMessage(), "Error");
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(new TransferResponseDTO(e.getMessage()));
       }
   }
    @GetMapping("/{accountId}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable UUID accountId) {
        logProducer.sendLog("GET /" + accountId +"/balance", "Request");

        try {
            BigDecimal balance = accountService.getAccountBalance(accountId);
            logProducer.sendLog("Fetched balance for AccountId: " + accountId + " = " + balance, "Success");
            return ResponseEntity.ok(balance);

        } catch (Exception e) {
            logProducer.sendLog("Failed to fetch balance: " + e.getMessage(), "Error");
            throw e;
        }
    }
}

