package com.Virtual_Bank_System.BFF.Client;

import com.Virtual_Bank_System.BFF.DTOs.TransactionDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name="transaction-service",url="${transaction-service.url}")
public interface TransactionServiceClient {
    @GetMapping("/accounts/{accountId}/transactions")
    List<TransactionDto> getAccountTransactions(@PathVariable UUID accountId);
}
