package com.Virtual_Bank_System.BFF.DTOs;

import com.Virtual_Bank_System.BFF.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private UUID accountId;
    private String accountNumber;
    private AccountType type;
    private double balance;

}

