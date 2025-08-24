package com.Virtual_Bank_System.User_Service.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"userId","userName","email","firstName","lastName"})
public class ProfileResponseDTO {
    private UUID UserId;
    private String UserName;
    private String Email;
    private String FirstName;
    private String LastName;


}
