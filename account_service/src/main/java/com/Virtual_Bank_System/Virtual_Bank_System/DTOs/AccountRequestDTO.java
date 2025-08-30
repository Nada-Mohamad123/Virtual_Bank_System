package com.Virtual_Bank_System.Virtual_Bank_System.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

import com.Virtual_Bank_System.Virtual_Bank_System.model.AccountType;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDTO {
    private UUID userId;
    private AccountType accountType;
    private BigDecimal initialBalance;

    
}
