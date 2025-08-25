package com.Virtual_Bank_System.Transaction.Service.Service;

import com.Virtual_Bank_System.Transaction.Service.DTOs.TransactionHistoryDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferExecutionRequestDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferInitiationRequestDto;
import com.Virtual_Bank_System.Transaction.Service.DTOs.TransferResponseDto;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Model.Transaction;
import com.Virtual_Bank_System.Transaction.Service.Model.Transaction.TransactionStatus;
import com.Virtual_Bank_System.Transaction.Service.Repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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
                .deliveryStatus(Transaction.DeliveryStatus.SENT)
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

        if (transaction.getStatus() != TransactionStatus.Initiated) {
            throw new TransactionException("Transaction already processed");
        }

        transaction.setStatus(TransactionStatus.Success);
        transaction.setTimestamp(Instant.now());
        transactionRepository.save(transaction);

        return TransferResponseDto.builder()
                .transactionId(transaction.getTransactionId())
                .status(transaction.getStatus())
                .timestamp(transaction.getTimestamp())
                .build();
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
