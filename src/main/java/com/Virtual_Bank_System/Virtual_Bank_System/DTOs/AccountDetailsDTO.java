package com.Virtual_Bank_System.Virtual_Bank_System.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

import com.Virtual_Bank_System.Virtual_Bank_System.model.accountStatus;
import com.Virtual_Bank_System.Virtual_Bank_System.model.accountType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsDTO {

    private UUID accountId;
    private String accountNumber;
    private accountType type;
    private BigDecimal balance;
    private accountStatus status;
}
