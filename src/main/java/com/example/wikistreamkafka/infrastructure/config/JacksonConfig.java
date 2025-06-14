package com.example.wikistreamkafka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Configuration class for Jackson ObjectMapper. Registers modules for handling Java 8 date/time
 * types like Instant.
 */
@Configuration
public class JacksonConfig {

  /**
   * Creates and configures the ObjectMapper bean with the JavaTimeModule.
   *
   * @return The configured ObjectMapper
   */
  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.registerModule(new JavaTimeModule());
    return objectMapper;
  }
}
