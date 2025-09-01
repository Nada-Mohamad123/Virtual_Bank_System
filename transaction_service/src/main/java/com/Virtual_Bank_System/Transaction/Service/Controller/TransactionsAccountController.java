package com.Virtual_Bank_System.Transaction.Service.Controller;

import com.Virtual_Bank_System.Transaction.Service.DTOs.TransactionHistoryDto;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Service.LogProducerService;
import com.Virtual_Bank_System.Transaction.Service.Service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/accounts")
public class TransactionsAccountController {
    private final TransactionService transactionService;
    private final LogProducerService logProducer;

    public TransactionsAccountController(TransactionService transactionService, LogProducerService logProducer) {
        this.transactionService = transactionService;
        this.logProducer = logProducer;
    }
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<?> getAccountTransactions(@PathVariable UUID accountId) {
        logProducer.sendLog("GET /accounts/" + accountId + "/transactions - Request received", "Request");

        try {
            List<TransactionHistoryDto> transactions = transactionService.getTransactionsByAccount(accountId);
            logProducer.sendLog(transactions, "Response");
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
