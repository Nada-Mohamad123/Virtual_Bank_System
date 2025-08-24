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
@JsonPropertyOrder({"userId","userName","message"})
public class RegisterResponseDTO {
    private UUID UserId;
    private String UserName;
    private String Message;

}
