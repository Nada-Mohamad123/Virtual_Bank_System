package com.Virtual_Bank_System.BFF.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID userId;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
}
