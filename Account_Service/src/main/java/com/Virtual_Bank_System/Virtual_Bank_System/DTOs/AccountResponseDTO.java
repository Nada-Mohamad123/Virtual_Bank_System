package com.Virtual_Bank_System.Virtual_Bank_System.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"accountId","accountNumber","message"})
public class AccountResponseDTO {
    private String accountId;
    private String accountNumber;
    private String message;
}
