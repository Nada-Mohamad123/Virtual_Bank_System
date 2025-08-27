package com.Virtual_Bank_System.logging.controller;


import com.Virtual_Bank_System.logging.dto.LogMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Service
public class LogProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public LogProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendLog(String message, String type) throws Exception {
        LogMessage logMessage = new LogMessage(message, type, Instant.now().toString());
        String json = objectMapper.writeValueAsString(logMessage);
        kafkaTemplate.send("logging-topic", json);
    }
}

