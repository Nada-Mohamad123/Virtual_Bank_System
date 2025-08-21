package com.Virtual_Bank_System.User_Service.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({ "status", "message", "error" })
public class ErrorResponseDTO {
    private int Status;
    private String Error;
    private String Message;


}
