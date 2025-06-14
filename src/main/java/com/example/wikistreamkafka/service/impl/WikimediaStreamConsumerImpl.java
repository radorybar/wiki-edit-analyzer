package com.example.wikistreamkafka.service.impl;

import com.example.wikistreamkafka.api.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.domain.model.WikiEvent;
import com.example.wikistreamkafka.infrastructure.mapper.WikiEventMapper;
import com.example.wikistreamkafka.service.api.KafkaProducerService;
import com.example.wikistreamkafka.service.api.ValidationService;
import com.example.wikistreamkafka.service.api.WikimediaStreamConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Implementation of the WikimediaStreamConsumer interface.
 * This service is responsible for consuming the Wikimedia stream and publishing events to Kafka.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WikimediaStreamConsumerImpl implements WikimediaStreamConsumer {

    private final WebClient wikimediaWebClient;
    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;
    private final WikiEventMapper wikiEventMapper;
    private final ValidationService validationService;

    /**
     * Starts consuming the Wikimedia stream and publishing events to Kafka.
     * This method is triggered when the application starts.
     */
    @EventListener(ApplicationStartedEvent.class)
    @Override
    public void consumeStreamAndPublishToKafka() {
        log.info("Started consuming Wikimedia stream...");

        wikimediaWebClient.get()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(
                        jsonData -> {
                            try {
                                log.debug("Received JSON data: {}", jsonData);

                                // Parse JSON to DTO
                                if (jsonData == null || jsonData.isBlank()) {
                                    log.warn("Received empty JSON data, skipping");
                                    return;
                                }

                                WikimediaRecentChangeDto dto = objectMapper.readValue(jsonData, WikimediaRecentChangeDto.class);
                                if (dto == null) {
                                    log.warn("Failed to parse JSON data to DTO, skipping");
                                    return;
                                }

                                // Convert DTO to domain model
                                WikiEvent wikiEvent = wikiEventMapper.toWikiEvent(dto);
                                if (wikiEvent == null) {
                                    log.warn("Failed to convert DTO to WikiEvent, skipping");
                                    return;
                                }

                                // Validate the domain model
                                if (!validationService.isValid(wikiEvent)) {
                                    log.warn("Invalid WikiEvent, skipping: {}", wikiEvent);
                                    return;
                                }

                                log.info("Processed event with ID: {}, Title: {}", wikiEvent.getId(), wikiEvent.getTitle());
                                kafkaProducerService.sendMessage(wikiEvent);
                            } catch (Exception e) {
                                log.error("Error processing JSON data: {}", e.getMessage(), e);
                            }
                        },
                        error -> log.error("Error in stream consumption: {}", error.getMessage(), error),
                        () -> log.info("Completed consuming Wikimedia stream")
                );
    }
}
