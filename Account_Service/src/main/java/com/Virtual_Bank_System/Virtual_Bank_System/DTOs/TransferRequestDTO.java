package com.Virtual_Bank_System.Virtual_Bank_System.DTOs;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data   // <-- this generates getters, setters, toString, equals, hashCode
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    private UUID fromAccountId;
    private UUID toAccountId;
    private BigDecimal amount;
    
    
    
}
