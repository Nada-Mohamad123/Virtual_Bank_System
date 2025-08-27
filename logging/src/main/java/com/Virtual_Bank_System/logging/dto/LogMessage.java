package com.Virtual_Bank_System.logging.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogMessage {
    private String message;
    private String messageType;
    private String dateTime; // ISO format (e.g., 2025-08-26T10:15:30Z)
}
