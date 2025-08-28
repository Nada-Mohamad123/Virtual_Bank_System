package com.Virtual_Bank_System.logging.service;

import com.Virtual_Bank_System.logging.dto.LogMessage;
import com.Virtual_Bank_System.logging.model.LogEntity;
import com.Virtual_Bank_System.logging.repository.LogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LogConsumer {

    private static final Logger logger = LoggerFactory.getLogger(LogConsumer.class);

    private final LogRepository logRepository;

    public LogConsumer(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @KafkaListener(topics = "logging-topic", groupId = "logging-service")
    public void consume(LogMessage logMessage) {
        try {
            logger.info("Received message: {}", logMessage);

            LogEntity logEntity = new LogEntity();
            logEntity.setMessage(logMessage.getMessage());
            logEntity.setMessageType(logMessage.getMessageType());
            logEntity.setDateTime(
                    logMessage.getDateTime() != null ? logMessage.getDateTime() : Instant.now()
            );

            logRepository.save(logEntity);
            logger.info("Saved log: {}", logEntity.getMessage());

        } catch (Exception e) {
            logger.error("Error processing log message: {}", e.getMessage(), e);
        }
    }
}
