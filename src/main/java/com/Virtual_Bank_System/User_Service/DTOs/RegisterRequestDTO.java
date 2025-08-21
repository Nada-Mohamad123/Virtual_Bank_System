package com.Virtual_Bank_System.User_Service.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {
    private String UserName;
    private String Password;
    private String Email;
    private String FirstName;
    private String LastName;

}
