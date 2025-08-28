package com.Virtual_Bank_System.logging.model;


import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "log_entity")
public class LogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String message;

    private String messageType;
    private Instant  dateTime;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getMessageType() { return messageType; }
    public void setMessageType(String messageType) { this.messageType = messageType; }

    public Instant  getDateTime() { return dateTime; }
    public void setDateTime(Instant  dateTime) { this.dateTime = dateTime; }
}
