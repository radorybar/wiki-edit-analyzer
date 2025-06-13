package com.example.wikistreamkafka.service;

import com.example.wikistreamkafka.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.mapper.WikiEventMapper;
import com.example.wikistreamkafka.model.WikiEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class WikimediaStreamConsumer {

    @Value("${wikimedia.stream.url}")
    private String wikiStreamUrl;

    private final KafkaProducerService kafkaProducerService;
    private final ObjectMapper objectMapper;
    private final WikiEventMapper wikiEventMapper;

    @EventListener(ApplicationStartedEvent.class)
    public void consumeStreamAndPublishToKafka() {
        log.info("Started consuming Wikimedia stream...");

        WebClient client = WebClient.builder()
                .baseUrl(wikiStreamUrl)
                .build();

        client.get()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(String.class)
                .subscribe(
                        jsonData -> {
                            try {
                                log.debug("Received JSON data: {}", jsonData);
                                WikimediaRecentChangeDto dto = objectMapper.readValue(jsonData, WikimediaRecentChangeDto.class);
                                WikiEvent wikiEvent = wikiEventMapper.toWikiEvent(dto);
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
