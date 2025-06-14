package com.example.wikistreamkafka.service.api;

import com.example.wikistreamkafka.api.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.domain.model.WikiEvent;

/**
 * Service interface for producing messages to Kafka. This interface defines methods for sending
 * different types of messages to Kafka.
 */
public interface KafkaProducerService {

  /**
   * Sends a raw message string to Kafka.
   *
   * @param message The message to send
   */
  void sendMessage(String message);

  /**
   * Sends a WikimediaRecentChangeDto to Kafka. The DTO is converted to JSON before sending.
   *
   * @param dto The DTO to send
   */
  void sendMessage(WikimediaRecentChangeDto dto);

  /**
   * Sends a WikiEvent domain model to Kafka. The domain model is converted to JSON before sending.
   *
   * @param event The WikiEvent domain model to send
   */
  void sendMessage(WikiEvent event);
}
