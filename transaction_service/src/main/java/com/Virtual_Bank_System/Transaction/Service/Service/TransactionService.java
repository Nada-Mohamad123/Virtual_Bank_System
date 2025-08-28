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
                .deliveryStatus(Transaction.DeliveryStatus.PENDING) // ✅ أضف القيمة هنا
                .timestamp(Instant.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        return TransferResponseDto.builder()
                .transactionId(saved.getTransactionId())
                .status(saved.getStatus())
                .timestamp(saved.getTimestamp())
                .build();
    }

public TransferResponseDto executeTransfer(TransferExecutionRequestDto request) {
    Transaction transaction = transactionRepository.findById(request.getTransactionId())
            .orElseThrow(() -> new TransactionException("Transaction not found"));

    // لو الترانزاكشن اتعمل له معالجة قبل كده
    if (transaction.getStatus() != TransactionStatus.Initiated) {
        throw new TransactionException("Transaction already processed");
    }

    // ✅ تحقق من الحسابات في Account Service
    boolean fromAccountExists = checkAccountExists(transaction.getFromAccountId());
    boolean toAccountExists = checkAccountExists(transaction.getToAccountId());

    if (!fromAccountExists || !toAccountExists) {
        // لو أي حساب مش موجود → فشل الترانزاكشن
        transaction.setStatus(TransactionStatus.Failed);
        transaction.setTimestamp(Instant.now());
        transactionRepository.save(transaction);

        throw new TransactionException("Invalid 'from' or 'to' account ID.");
    }

    // ✅ الحسابات موجودة → اكمل الترانزاكشن
    transaction.setStatus(TransactionStatus.Success);
    transaction.setDeliveryStatus(Transaction.DeliveryStatus.SENT); // ممكن تغير لـ DELIVERED لو تحبي
    transaction.setTimestamp(Instant.now());
    transactionRepository.save(transaction);

    return TransferResponseDto.builder()
            .transactionId(transaction.getTransactionId())
            .status(transaction.getStatus())
            .timestamp(transaction.getTimestamp())
            .build();
}

private boolean checkAccountExists(UUID accountId) {
    try {
        String url = "http://localhost:8082/accounts/" + accountId; // أو account-service:8082 لو Docker
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getStatusCode() == HttpStatus.OK;
 
    } catch (Exception e) {
        e.printStackTrace(); // هتشوفي لو فيه مشكلة
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
