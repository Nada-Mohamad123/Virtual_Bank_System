package com.Virtual_Bank_System.Transaction.Service.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonPropertyOrder({"status","error","message"})
public class ErrorResponseDto {
    private int status;
    private String error;
    private String message;

}
