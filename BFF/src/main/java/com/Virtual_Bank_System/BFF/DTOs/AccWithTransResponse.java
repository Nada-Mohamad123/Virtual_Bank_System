package com.Virtual_Bank_System.BFF.DTOs;

import java.util.List;
import java.util.UUID;

import com.Virtual_Bank_System.BFF.AccountType;
import lombok.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccWithTransResponse {
    private UUID accountId;
    private String accountNumber;
    private AccountType type;
    private double balance;
    private List<TransactionResponse> transactions;
}
