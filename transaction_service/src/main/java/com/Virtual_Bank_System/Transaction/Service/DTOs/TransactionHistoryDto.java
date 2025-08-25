package com.Virtual_Bank_System.Transaction.Service.DTOs;

import com.Virtual_Bank_System.Transaction.Service.Model.Transaction.TransactionStatus;
import com.Virtual_Bank_System.Transaction.Service.Model.Transaction.DeliveryStatus;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TransactionHistoryDto {
    private UUID transactionId;
    private UUID fromAccountId;
    private UUID toAccountId;
    private Double amount;
    private String description;
    private Instant timestamp;
    private TransactionStatus status;
    private DeliveryStatus deliveryStatus;
}
