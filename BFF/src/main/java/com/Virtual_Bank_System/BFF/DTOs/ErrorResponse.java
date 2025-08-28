package com.Virtual_Bank_System.BFF.DTOs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonPropertyOrder({"status","error","message"})
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
}