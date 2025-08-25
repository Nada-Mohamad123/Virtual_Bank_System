package com.Virtual_Bank_System.Virtual_Bank_System.service;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountDetailsDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.TransferRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.TransferResponseDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.model.account;
import com.Virtual_Bank_System.Virtual_Bank_System.model.accountStatus;
import com.Virtual_Bank_System.Virtual_Bank_System.model.accountType;
import com.Virtual_Bank_System.Virtual_Bank_System.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    // Create a new account
    public account createAccount(AccountRequestDTO dto) {
        if (dto.getInitialBalance() == null || dto.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid account type or initial balance.");
        }


        account acc = new account();
        acc.setUserId(dto.getUserId());
        acc.setAccountType(dto.getAccountType()); // save account type
        acc.setBalance(dto.getInitialBalance());
        acc.setAccountNumber(generateUniqueAccountNumber()); // auto-generate account number
        acc.setStatus(accountStatus.ACTIVE);

        return accountRepository.save(acc);
    }

// Get accounts by accountId
public AccountDetailsDTO getAccountById(UUID accountId) {
    account acc = accountRepository.findById(accountId)
            .orElseThrow(() -> new EntityNotFoundException("Account not found"));

    return new AccountDetailsDTO(
            acc.getId(),
            acc.getAccountNumber(),
            acc.getAccountType(),
            acc.getBalance(),
            acc.getStatus()
    );
}

// Get accounts by userId
public List<account> getAccountsByUser(UUID userId) {
    List<account> accounts = accountRepository.findByUserId(userId);
    if (accounts.isEmpty()) {
        throw new EntityNotFoundException("No accounts found for user: " + userId);
    }
    return accounts;
}


// ✅ Transfer money between accounts
@Transactional
public void transferFunds(TransferRequestDTO dto) {
    if (dto.getFromAccountId() == null || dto.getToAccountId() == null || dto.getAmount() == null) {
        throw new IllegalArgumentException("fromAccountId, toAccountId, and amount are required");
    }

    if (dto.getFromAccountId().equals(dto.getToAccountId())) {
        throw new IllegalArgumentException("Cannot transfer to the same account");
    }

    account fromAccount = accountRepository.findById(dto.getFromAccountId())
            .orElseThrow(() -> new EntityNotFoundException("Source account not found"));

    account toAccount = accountRepository.findById(dto.getToAccountId())
            .orElseThrow(() -> new EntityNotFoundException("Destination account not found"));

    if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
        throw new IllegalArgumentException("Insufficient balance in source account");
    }

    fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
    toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));

    accountRepository.save(fromAccount);
    accountRepository.save(toAccount);
}

    
    // ---------------- Helper method ----------------
    private String generateUniqueAccountNumber() {
        String accountNumber;
        Optional<account> existing;

        // Keep generating until a unique number is found
        do {
            long number = (long) (Math.random() * 1_000_000_0000L); // random 10-digit number
            accountNumber = String.format("%010d", number);
            existing = accountRepository.findByAccountNumber(accountNumber);
        } while (existing.isPresent());

        return accountNumber;
    }
}
