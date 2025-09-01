package com.Virtual_Bank_System.Virtual_Bank_System.service;

import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountDetailsDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.AccountRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.DTOs.TransferRequestDTO;
import com.Virtual_Bank_System.Virtual_Bank_System.model.Account;
import com.Virtual_Bank_System.Virtual_Bank_System.model.accountStatus;
import com.Virtual_Bank_System.Virtual_Bank_System.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    //  Mark accounts inactive if no transaction in the last minute
    public void InactiveAccounts() {
        LocalDateTime cutoffTime = LocalDateTime.now().minusHours(24);
        List<Account> staleAccounts = accountRepository.findStaleAccounts(cutoffTime);
        for (Account acc : staleAccounts) {
            acc.setStatus(accountStatus.INACTIVE);
        }
        accountRepository.saveAll(staleAccounts);
    }

    //  Create a new account
    public Account createAccount(AccountRequestDTO dto) {
        if (dto.getInitialBalance() == null || dto.getInitialBalance().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid initial balance.");
        }

        Account acc = new Account();
        acc.setUserId(dto.getUserId());
        acc.setAccountType(dto.getAccountType());
        acc.setBalance(dto.getInitialBalance());
        acc.setAccountNumber(generateUniqueAccountNumber());
        acc.setStatus(accountStatus.ACTIVE);

        return accountRepository.save(acc);
    }

    //  Get account details by accountId
    public AccountDetailsDTO getAccountById(UUID accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        return new AccountDetailsDTO(
                acc.getId(),
                acc.getAccountNumber(),
                acc.getAccountType(),
                acc.getBalance(),
                acc.getStatus()
        );
    }

    //  Get all accounts for a user
    public List<AccountDetailsDTO> getAccountsByUser(UUID userId) {
        List<Account> accounts = accountRepository.findByUserId(userId);
        if (accounts.isEmpty()) {
            throw new EntityNotFoundException("No accounts found for user ID " + userId);
        }

        return accounts.stream()
                .map(acc -> new AccountDetailsDTO(
                        acc.getId(),
                        acc.getAccountNumber(),
                        acc.getAccountType(),
                        acc.getBalance(),
                        acc.getStatus()
                ))
                .toList();
    }

    //  Transfer money between accounts
    @Transactional
    public void transferFunds(TransferRequestDTO dto) {
        if (dto.getFromAccountId() == null || dto.getToAccountId() == null || dto.getAmount() == null) {
            throw new IllegalArgumentException("fromAccountId, toAccountId, and amount are required");
        }

        if (dto.getFromAccountId().equals(dto.getToAccountId())) {
            throw new IllegalArgumentException("Cannot transfer to the same account");
        }

        Account fromAccount = accountRepository.findById(dto.getFromAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Source account not found"));

        Account toAccount = accountRepository.findById(dto.getToAccountId())
                .orElseThrow(() -> new EntityNotFoundException("Destination account not found"));

        if (fromAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance in source account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(dto.getAmount()));
        fromAccount.setStatus(accountStatus.ACTIVE);
        fromAccount.setLastTransactionAt(LocalDateTime.now());

        toAccount.setBalance(toAccount.getBalance().add(dto.getAmount()));
        toAccount.setStatus(accountStatus.ACTIVE);
        toAccount.setLastTransactionAt(LocalDateTime.now());

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //  Get only the balance of an account (for TransactionService)
    public BigDecimal getAccountBalance(UUID accountId) {
        Account acc = accountRepository.findById(accountId)
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));
        return acc.getBalance();
    }

    // ---------------- Helper method ----------------
    private String generateUniqueAccountNumber() {
        String accountNumber;
        Optional<Account> existing;

        do {
            long number = (long) (Math.random() * 1_000_000_0000L); // random 10-digit number
            accountNumber = String.format("%010d", number);
            existing = accountRepository.findByAccountNumber(accountNumber);
        } while (existing.isPresent());

        return accountNumber;
    }
}
