package com.Virtual_Bank_System.logging.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
public class LogMessage {
    private String message;
    private String messageType;
    private Instant dateTime;

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public Instant getDateTime() { return dateTime; }
    public void setDateTime(Instant dateTime) { this.dateTime = dateTime; }

    @Override
    public String toString() {
        return "LogMessage{" +
                "message='" + message + '\'' +
                ", messageType='" + messageType + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
