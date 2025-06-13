package com.example.wikistreamkafka.service;

import com.example.wikistreamkafka.config.ApplicationConfig;
import com.example.wikistreamkafka.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.model.WikiEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationConfig applicationConfig;

    public void sendMessage(String message) {
        String topicName = applicationConfig.getKafka().getTopicName();
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("Sent message=[{}] with offset=[{}]", 
                        message, 
                        result.getRecordMetadata().offset());
            } else {
                log.error("Unable to send message=[{}] due to : {}", message, ex.getMessage());
            }
        });
    }

    public void sendMessage(WikimediaRecentChangeDto dto) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(dto);
            String topicName = applicationConfig.getKafka().getTopicName();
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, jsonMessage);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent DTO message with ID=[{}] and offset=[{}]", 
                            dto.getId(), 
                            result.getRecordMetadata().offset());
                } else {
                    log.error("Unable to send DTO message with ID=[{}] due to : {}", dto.getId(), ex.getMessage());
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Error serializing DTO to JSON: {}", e.getMessage(), e);
        }
    }

    /**
     * Sends a WikiEvent domain model to Kafka.
     * The domain model is converted to JSON before sending.
     *
     * @param event The WikiEvent domain model to send
     */
    public void sendMessage(WikiEvent event) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(event);
            String topicName = applicationConfig.getKafka().getTopicName();
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, jsonMessage);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent WikiEvent message with ID=[{}] and offset=[{}]", 
                            event.getId(), 
                            result.getRecordMetadata().offset());
                } else {
                    log.error("Unable to send WikiEvent message with ID=[{}] due to : {}", event.getId(), ex.getMessage());
                }
            });
        } catch (JsonProcessingException e) {
            log.error("Error serializing WikiEvent to JSON: {}", e.getMessage(), e);
        }
    }
}
