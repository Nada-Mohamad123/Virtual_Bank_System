package com.Virtual_Bank_System.Virtual_Bank_System.controller;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.*;
import com.Virtual_Bank_System.Virtual_Bank_System.model.Account;
import com.Virtual_Bank_System.Virtual_Bank_System.service.AccountService;
import com.Virtual_Bank_System.Virtual_Bank_System.service.LogProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private LogProducerService logProducer;

    // Create new account and return DTO response
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO dto) {
        logProducer.sendLog("POST /accounts - Request received", "Request");
        try {
            Account savedAccount = accountService.createAccount(dto);

            AccountResponseDTO response = new AccountResponseDTO(
                    savedAccount.getId().toString(),
                    savedAccount.getAccountNumber(),
                    "Account created successfully."
            );

            logProducer.sendLog("Account created successfully: " + savedAccount.getAccountNumber(), "Success");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            logProducer.sendLog("Failed to create account: " + e.getMessage(), "Error");
            throw e;
        }
    }
@GetMapping("/{accountId}")

public ResponseEntity<AccountDetailsDTO> getAccountById(@PathVariable UUID accountId) {
    logProducer.sendLog("GET /accounts/" + accountId + " - Request received", "Request");
    AccountDetailsDTO details = accountService.getAccountById(accountId);
    logProducer.sendLog("Fetched account details for ID: " + accountId, "Success");
    return ResponseEntity.ok(details);
}

   //  Transfer funds using UUID IDs
   @PutMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequestDTO dto) {
       logProducer.sendLog("PUT /accounts/transfer - Request received", "Request");

       try {
           accountService.transferFunds(dto);

           TransferResponseDTO response = new TransferResponseDTO("Transfer completed successfully.");
           logProducer.sendLog("Funds transferred successfully", "Success");

           return ResponseEntity.ok(response);

       } catch (Exception e) {
           logProducer.sendLog("Failed to transfer funds: " + e.getMessage(), "Error");
           throw e;
       }
   }
}

