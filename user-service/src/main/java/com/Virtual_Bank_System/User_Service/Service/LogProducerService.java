package com.Virtual_Bank_System.User_Service.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class LogProducerService {

    private static final Logger logger = LoggerFactory.getLogger(LogProducerService.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public LogProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendLog(String message, String messageType) {
        Map<String, Object> payload = Map.of(
                "message", message,
                "messageType", messageType,
                "dateTime", Instant.now().toString()
        );

        try {
            String json = objectMapper.writeValueAsString(payload);

            kafkaTemplate.send("logging-topic", json)
                    .whenComplete((result, ex) -> {
                        if (ex != null) {
                            logger.error("Failed to send log: {}", ex.getMessage(), ex);
                        } else {
                            RecordMetadata metadata = result.getRecordMetadata();
                            logger.info("Log sent successfully: {} (topic={}, partition={}, offset={})",
                                    message, metadata.topic(), metadata.partition(), metadata.offset());
                        }
                    });

        } catch (JsonProcessingException e) {
            logger.error("Error serializing log message", e);
        }
    }
}
