package com.Virtual_Bank_System.Transaction.Service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferInitiationRequestDto {
    private UUID fromAccountId;
    private UUID toAccountId;
    private double amount;
    private String description;
}
