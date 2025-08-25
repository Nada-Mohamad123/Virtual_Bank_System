package com.Virtual_Bank_System.Virtual_Bank_System.controller;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountDetailsDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.model.account;
import com.Virtual_Bank_System.Virtual_Bank_System.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users/{userId}/accounts")
public class UserAccountController {
    @Autowired
    AccountService accountService;
    // Get all accounts for a user by userId
    @GetMapping
    public ResponseEntity<List<AccountDetailsDTO>> getAccountsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(accountService.getAccountsByUser(userId));
    }
}
