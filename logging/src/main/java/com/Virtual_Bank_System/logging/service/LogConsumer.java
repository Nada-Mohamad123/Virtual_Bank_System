package com.Virtual_Bank_System.logging.service;

import com.Virtual_Bank_System.logging.dto.LogMessage;
import com.Virtual_Bank_System.logging.model.LogEntity;
import com.Virtual_Bank_System.logging.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class LogConsumer {

    private final LogRepository logRepository;
    private final ObjectMapper objectMapper;

    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
        this.objectMapper = new ObjectMapper();
    }

    @KafkaListener(topics = "logging-topic", groupId = "logging-group")
    public void consume(String messageJson) {
        System.out.println("Received raw message: " + messageJson);
        try {
            LogMessage logMessage = objectMapper.readValue(messageJson, LogMessage.class);
            LogEntity entity = new LogEntity();
            entity.setMessage(logMessage.getMessage());
            entity.setMessageType(logMessage.getMessageType());
            entity.setDateTime(Instant.parse(logMessage.getDateTime()));
            logRepository.save(entity);
            System.out.println("Saved log: " + entity.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
