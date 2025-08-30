package com.Virtual_Bank_System.Transaction.Service.Service;

import com.Virtual_Bank_System.Transaction.Service.DTOs.TransactionHistoryDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferExecutionRequestDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferInitiationRequestDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferResponseDto;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Model.Transaction;
import com.Virtual_Bank_System.Transaction.Service.Model.Transaction.TransactionStatus;
import com.Virtual_Bank_System.Transaction.Service.Repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final RestTemplate restTemplate;

    public TransactionService(TransactionRepository transactionRepository, RestTemplate restTemplate) {
        this.transactionRepository = transactionRepository;
        this.restTemplate = restTemplate;
    }

    // ---------------- Initiate ----------------
    public TransferResponseDto initiateTransfer(TransferInitiationRequestDto request) {
        if (request.getFromAccountId() == null || request.getToAccountId() == null) {
            throw new TransactionException("Invalid 'from' or 'to' account ID.");
        }
        if (request.getAmount() <= 0) {
            throw new TransactionException("Amount must be greater than zero.");
        }

        Transaction transaction = Transaction.builder()
                .fromAccountId(request.getFromAccountId())
                .toAccountId(request.getToAccountId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .status(TransactionStatus.Initiated)
                .deliveryStatus(Transaction.DeliveryStatus.PENDING)
                .timestamp(Instant.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        return TransferResponseDto.builder()
                .transactionId(saved.getTransactionId())
                .status(saved.getStatus())
                .timestamp(saved.getTimestamp())
                .build();
    }

    // ---------------- Execute ----------------
    public TransferResponseDto executeTransfer(TransferExecutionRequestDto request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId())
                .orElseThrow(() -> new TransactionException("Transaction not found"));

        if (transaction.getStatus() != TransactionStatus.Initiated) {
            throw new TransactionException("Transaction already processed");
        }

        boolean fromAccountExists = checkAccountExists(transaction.getFromAccountId());
        boolean toAccountExists = checkAccountExists(transaction.getToAccountId());

        if (!fromAccountExists || !toAccountExists) {
            transaction.setStatus(TransactionStatus.Failed);
            transaction.setTimestamp(Instant.now());
            transactionRepository.save(transaction);
            throw new TransactionException("Invalid 'from' or 'to' account ID.");
        }

        // ✅ Call Account-Service to perform transfer
        boolean transferSuccess = callAccountServiceTransfer(
                transaction.getFromAccountId(),
                transaction.getToAccountId(),
                transaction.getAmount()
        );

        if (!transferSuccess) {
            transaction.setStatus(TransactionStatus.Failed);
            transaction.setTimestamp(Instant.now());
            transactionRepository.save(transaction);
            throw new TransactionException("Balance update failed in Account-Service.");
        }

        // ✅ If success
        transaction.setStatus(TransactionStatus.Success);
        transaction.setDeliveryStatus(Transaction.DeliveryStatus.SENT);
        transaction.setTimestamp(Instant.now());
        transactionRepository.save(transaction);

        return TransferResponseDto.builder()
                .transactionId(transaction.getTransactionId())
                .status(transaction.getStatus())
                .timestamp(transaction.getTimestamp())
                .build();
    }

    // ---------------- Helpers ----------------
    private boolean checkAccountExists(UUID accountId) {
        try {
            String url = "http://localhost:8082/accounts/" + accountId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return response.getStatusCode() == HttpStatus.OK;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean callAccountServiceTransfer(UUID fromAccountId, UUID toAccountId, double amount) {
        try {
            String url = "http://localhost:8082/accounts/transfer";

TransferInitiationRequestDto dto = new TransferInitiationRequestDto();
dto.setFromAccountId(fromAccountId);
dto.setToAccountId(toAccountId);
dto.setAmount(amount);
dto.setDescription("Fund transfer");

            restTemplate.put(url, dto); // use PUT because Account-Service expects @PutMapping
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<TransactionHistoryDto> getTransactionsByAccount(UUID accountId) {
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(t -> t.getFromAccountId().equals(accountId) || t.getToAccountId().equals(accountId))
                .collect(Collectors.toList());

        if (transactions.isEmpty()) {
            throw new TransactionException("No transactions found for account ID " + accountId);
        }

        return transactions.stream()
                .map(t -> TransactionHistoryDto.builder()
                        .transactionId(t.getTransactionId())
                        .fromAccountId(t.getFromAccountId())
                        .toAccountId(t.getToAccountId())
                        .amount(t.getAmount())
                        .description(t.getDescription())
                        .timestamp(t.getTimestamp())
                        .status(t.getStatus())
                        .deliveryStatus(t.getDeliveryStatus())
                        .build())
                .collect(Collectors.toList());
    }
}
