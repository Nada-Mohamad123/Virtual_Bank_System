package com.Virtual_Bank_System.Virtual_Bank_System.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponseDTO {
    private String accountId;
    private String accountNumber;
    private String message;
}
