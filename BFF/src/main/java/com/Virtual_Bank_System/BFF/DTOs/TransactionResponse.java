package com.Virtual_Bank_System.BFF.DTOs;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private UUID transactionId;
    private double amount;
    private UUID toAccountId;
    private String description;
    private LocalDateTime timestamp;
}
