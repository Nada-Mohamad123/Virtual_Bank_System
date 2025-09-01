package com.Virtual_Bank_System.Transaction.Service.DTOs;

import com.Virtual_Bank_System.Transaction.Service.Model.Transaction;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"transactionId","status","timestamp"})
public class TransferResponseDto {
    private UUID transactionId;
    private Transaction.TransactionStatus status;
    private Instant timestamp;
}
