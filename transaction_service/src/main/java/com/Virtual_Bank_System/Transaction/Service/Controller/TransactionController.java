package com.Virtual_Bank_System.Transaction.Service.Controller;

import com.Virtual_Bank_System.Transaction.Service.DTOs.*;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Service.TransactionService;
import com.Virtual_Bank_System.Transaction.Service.Service.LogProducerService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> initiateTransfer(@RequestBody TransferInitiationRequestDto request) {
        logProducer.sendLog(request, "Request");

        try {
            TransferResponseDto response = transactionService.initiateTransfer(request);
            logProducer.sendLog(response, "Response");
            return ResponseEntity.ok(response);
        } catch (TransactionException e) {
            logProducer.sendLog("Failed to initiate transfer: " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            logProducer.sendLog("Unexpected error during initiation: " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "Something went wrong"
            ));
        }
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<?> executeTransfer(@RequestBody TransferExecutionRequestDto request) {
        logProducer.sendLog(request, "Request");

        try {
            TransferResponseDto response = transactionService.executeTransfer(request);
            logProducer.sendLog(response, "Response");
            return ResponseEntity.ok(response);
        } catch (TransactionException e) {
            logProducer.sendLog("Failed to execute transfer: " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "status", HttpStatus.BAD_REQUEST.value(),
                    "error", "Bad Request",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            logProducer.sendLog("Unexpected error during execution: " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "Something went wrong"
            ));
        }
    }

    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<?> getAccountTransactions(@PathVariable UUID accountId) {
        logProducer.sendLog("GET /transactions/accounts/" + accountId + "/transactions - Request received", "Request");;

        try {
            List<TransactionHistoryDto> transactions = transactionService.getTransactionsByAccount(accountId);
            logProducer.sendLog(transactions, "Response");
            return ResponseEntity.ok(transactions);
        } catch (TransactionException e) {
            logProducer.sendLog("Failed to fetch transactions for accountId=" + accountId + ": " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "status", HttpStatus.NOT_FOUND.value(),
                    "error", "Not Found",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            logProducer.sendLog("Unexpected error while fetching transactions: " + e.getMessage(), "Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "error", "Internal Server Error",
                    "message", "Something went wrong"
            ));
        }
    }
}
