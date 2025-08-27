package com.Virtual_Bank_System.BFF.DTOs;
import lombok.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountWithTrans {
    private AccountDto account;
    private List<TransactionDto> transactions;
}
