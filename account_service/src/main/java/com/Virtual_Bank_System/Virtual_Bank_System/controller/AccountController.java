package com.Virtual_Bank_System.Virtual_Bank_System.controller;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountDetailsDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountResponseDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.TransferRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.TransferResponseDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.model.account;
import com.Virtual_Bank_System.Virtual_Bank_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    // ✅ Create new account and return DTO response
    @PostMapping
    public ResponseEntity<AccountResponseDTO> createAccount(@RequestBody AccountRequestDTO dto) {
        account savedAccount = accountService.createAccount(dto);

        AccountResponseDTO response = new AccountResponseDTO(
                savedAccount.getId().toString(),
                savedAccount.getAccountNumber(),
                "Account created successfully."
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
@GetMapping("/{accountId}")
public ResponseEntity<AccountDetailsDTO> getAccountById(@PathVariable UUID accountId) {
    return ResponseEntity.ok(accountService.getAccountById(accountId));
}

   //  Transfer funds using UUID IDs
   @PutMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransferRequestDTO dto) {
    accountService.transferFunds(dto);
    return ResponseEntity.ok("{\"message\":\"Account updated successfully.\"}");
}

}
