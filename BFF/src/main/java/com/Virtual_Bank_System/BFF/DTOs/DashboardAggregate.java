package com.Virtual_Bank_System.BFF.DTOs;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardAggregate {
    private UserDto user;
    private List<AccountWithTrans> accounts;
}
