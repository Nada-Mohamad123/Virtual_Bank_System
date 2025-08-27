package com.Virtual_Bank_System.BFF.DTOs;

import java.util.List;
import java.util.UUID;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardResponse {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<AccWithTransResponse> accounts;
}
