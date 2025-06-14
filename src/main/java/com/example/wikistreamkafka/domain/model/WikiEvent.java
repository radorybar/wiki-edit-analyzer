package com.example.wikistreamkafka.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.Instant;

/**
 * Domain model representing a Wiki event.
 * This class contains the essential information about a wiki edit event.
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiEvent {
    @NotBlank(message = "Event ID cannot be blank")
    private String id;

    @NotBlank(message = "Event type cannot be blank")
    private String type;

    @NotBlank(message = "Title cannot be blank")
    private String title;

    private String user;

    @NotNull(message = "Timestamp cannot be null")
    @PastOrPresent(message = "Timestamp must be in the past or present")
    private Long timestamp;

    private String comment;

    private String server_url;

    @NotBlank(message = "Wiki cannot be blank")
    private String wiki;

    private Boolean bot;

    @NotNull(message = "Meta information cannot be null")
    @Valid
    private Meta meta;

    /**
     * Nested class representing metadata about the wiki event.
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Meta {
        @NotBlank(message = "URI cannot be blank")
        private String uri;

        @NotBlank(message = "Domain cannot be blank")
        private String domain;

        private String dt;

        @NotBlank(message = "Stream cannot be blank")
        private String stream;

        @NotBlank(message = "Topic cannot be blank")
        private String topic;
    }
}