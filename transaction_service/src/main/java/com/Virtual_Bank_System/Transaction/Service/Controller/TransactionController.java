package com.Virtual_Bank_System.Transaction.Service.Controller;

import com.Virtual_Bank_System.Transaction.Service.DTOs.*;
import com.Virtual_Bank_System.Transaction.Service.Exception.TransactionException;
import com.Virtual_Bank_System.Transaction.Service.Service.TransactionService;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransferResponseDto> initiateTransfer(@RequestBody TransferInitiationRequestDto request) {
        // تحقق يدوي من القيم داخل الخدمة
        TransferResponseDto response = transactionService.initiateTransfer(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransferResponseDto> executeTransfer(@RequestBody TransferExecutionRequestDto request) {
        // تحقق يدوي من القيم داخل الخدمة
        TransferResponseDto response = transactionService.executeTransfer(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/accounts/{accountId}/transactions")
public ResponseEntity<?> getAccountTransactions(@PathVariable UUID accountId) {
    try {
        List<TransactionHistoryDto> transactions = transactionService.getTransactionsByAccount(accountId);
        return ResponseEntity.ok(transactions);
    } catch (TransactionException e) {
        // الاستثناء تم توليده في الخدمة
        return ResponseEntity.status(404).body(Map.of(
                "status", 404,
                "error", "Not Found",
                "message", e.getMessage()
        ));
    }
}

}
