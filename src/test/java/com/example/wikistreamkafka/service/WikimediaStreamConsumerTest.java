package com.example.wikistreamkafka.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.wikistreamkafka.api.dto.WikimediaRecentChangeDto;
import com.example.wikistreamkafka.domain.model.WikiEvent;
import com.example.wikistreamkafka.infrastructure.mapper.WikiEventMapper;
import com.example.wikistreamkafka.service.api.KafkaProducerService;
import com.example.wikistreamkafka.service.api.ValidationService;
import com.example.wikistreamkafka.service.impl.WikimediaStreamConsumerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@ExtendWith(MockitoExtension.class)
class WikimediaStreamConsumerTest {

  @Mock private WebClient wikimediaWebClient;

  @Mock private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

  @Mock private WebClient.RequestHeadersSpec requestHeadersSpec;

  @Mock private WebClient.ResponseSpec responseSpec;

  @Mock private KafkaProducerService kafkaProducerService;

  @Mock private ObjectMapper objectMapper;

  @Mock private WikiEventMapper wikiEventMapper;

  @Mock private ValidationService validationService;

  @InjectMocks private WikimediaStreamConsumerImpl wikimediaStreamConsumer;

  private WikimediaRecentChangeDto validDto;
  private WikiEvent validWikiEvent;
  private String validJsonData;

  @BeforeEach
  void setUp() throws Exception {
    // Setup WebClient mock chain
    when(wikimediaWebClient.get()).thenReturn(requestHeadersUriSpec);
    when(requestHeadersUriSpec.accept(any(MediaType.class))).thenReturn(requestHeadersSpec);
    when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);

    // Create test data
    validJsonData = "{\"id\":\"123\",\"type\":\"edit\"}";

    validDto = new WikimediaRecentChangeDto();
    // Set properties on validDto

    validWikiEvent = new WikiEvent();
    validWikiEvent.setId("123");
    validWikiEvent.setType("edit");
    validWikiEvent.setTitle("Test Page");

    WikiEvent.Meta meta = new WikiEvent.Meta();
    meta.setUri("https://en.wikipedia.org/wiki/Test_Page");
    meta.setDomain("en.wikipedia.org");
    meta.setStream("mediawiki.recentchange");
    meta.setTopic("eqiad.mediawiki.recentchange");
    validWikiEvent.setMeta(meta);
  }

  @Test
  void consumeStreamAndPublishToKafka_withValidData_sendsMessageToKafka() throws Exception {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(validJsonData));
    when(objectMapper.readValue(validJsonData, WikimediaRecentChangeDto.class))
        .thenReturn(validDto);
    when(wikiEventMapper.toWikiEvent(validDto)).thenReturn(validWikiEvent);
    when(validationService.isValid(validWikiEvent)).thenReturn(true);

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService).sendMessage(validWikiEvent);
  }

  @Test
  void consumeStreamAndPublishToKafka_withNullJsonData_doesNotSendMessageToKafka() {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.empty());

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }

  @Test
  void consumeStreamAndPublishToKafka_withEmptyJsonData_doesNotSendMessageToKafka() {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(""));

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }

  @Test
  void consumeStreamAndPublishToKafka_withNullDto_doesNotSendMessageToKafka() throws Exception {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(validJsonData));
    when(objectMapper.readValue(validJsonData, WikimediaRecentChangeDto.class)).thenReturn(null);

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }

  @Test
  void consumeStreamAndPublishToKafka_withNullWikiEvent_doesNotSendMessageToKafka()
      throws Exception {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(validJsonData));
    when(objectMapper.readValue(validJsonData, WikimediaRecentChangeDto.class))
        .thenReturn(validDto);
    when(wikiEventMapper.toWikiEvent(validDto)).thenReturn(null);

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }

  @Test
  void consumeStreamAndPublishToKafka_withInvalidWikiEvent_doesNotSendMessageToKafka()
      throws Exception {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(validJsonData));
    when(objectMapper.readValue(validJsonData, WikimediaRecentChangeDto.class))
        .thenReturn(validDto);
    when(wikiEventMapper.toWikiEvent(validDto)).thenReturn(validWikiEvent);
    when(validationService.isValid(validWikiEvent)).thenReturn(false);

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }

  @Test
  void consumeStreamAndPublishToKafka_withException_doesNotSendMessageToKafka() throws Exception {
    // Arrange
    when(responseSpec.bodyToFlux(String.class)).thenReturn(Flux.just(validJsonData));
    when(objectMapper.readValue(anyString(), eq(WikimediaRecentChangeDto.class)))
        .thenThrow(new RuntimeException("Test exception"));

    // Act
    wikimediaStreamConsumer.consumeStreamAndPublishToKafka();

    // Assert
    verify(kafkaProducerService, never()).sendMessage(any(WikiEvent.class));
  }
}
