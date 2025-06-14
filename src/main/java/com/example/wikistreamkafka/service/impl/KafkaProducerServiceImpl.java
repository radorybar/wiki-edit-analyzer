package com.example.wikistreamkafka.service.impl;

import com.example.wikistreamkafka.infrastructure.config.ApplicationConfig;
import com.example.wikistreamkafka.api.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.domain.model.WikiEvent;
import com.example.wikistreamkafka.service.api.KafkaProducerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Implementation of the KafkaProducerService interface.
 * This service is responsible for sending messages to Kafka.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final ApplicationConfig applicationConfig;

    @Override
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

    @Override
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

    @Override
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
