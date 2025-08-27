package com.Virtual_Bank_System.BFF.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto {
    private UUID transactionId;
    private double amount;
    private UUID toAccountId;
    private String description;
    private LocalDateTime timestamp;
}
