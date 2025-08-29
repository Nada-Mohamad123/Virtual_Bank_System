package com.Virtual_Bank_System.Transaction.Service.Controller;

import com.Virtual_Bank_System.Transaction.Service.DTOs.*;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Service.TransactionService;
import com.Virtual_Bank_System.Transaction.Service.Service.LogProducerService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;
    private final LogProducerService logProducer;

    public TransactionController(TransactionService transactionService, LogProducerService logProducer) {
        this.transactionService = transactionService;
        this.logProducer = logProducer;
    }

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransferResponseDto> initiateTransfer(@RequestBody TransferInitiationRequestDto request) {
        logProducer.sendLog("POST /transactions/transfer/initiation - Request received", "Request");

        try {
            TransferResponseDto response = transactionService.initiateTransfer(request);
            logProducer.sendLog("Transfer initiation successful for source=" + request.getFromAccountId(), "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logProducer.sendLog("Failed to initiate transfer: " + e.getMessage(), "Error");
            throw e;
        }
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransferResponseDto> executeTransfer(@RequestBody TransferExecutionRequestDto request) {
        logProducer.sendLog("POST /transactions/transfer/execution - Request received", "Request");

        try {
            TransferResponseDto response = transactionService.executeTransfer(request);
            logProducer.sendLog("Transfer execution successful for transactionId=" + request.getTransactionId(), "Success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logProducer.sendLog("Failed to execute transfer: " + e.getMessage(), "Error");
            throw e;
        }
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<?> getAccountTransactions(@PathVariable UUID accountId) {
        logProducer.sendLog("GET /transactions/accounts/" + accountId + "/transactions - Request received", "Request");

        try {
            List<TransactionHistoryDto> transactions = transactionService.getTransactionsByAccount(accountId);
            logProducer.sendLog("Fetched " + transactions.size() + " transactions for accountId=" + accountId, "Success");
            return ResponseEntity.ok(transactions);
        } catch (TransactionException e) {
            logProducer.sendLog("Failed to fetch transactions for accountId=" + accountId + ": " + e.getMessage(), "Error");

            return ResponseEntity.status(404).body(Map.of(
                    "status", 404,
                    "error", "Not Found",
                    "message", e.getMessage()
            ));
        }
    }
}
