package com.example.wikistreamkafka.domain.model;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

/**
 * Domain model representing a Wiki event. This class contains the essential information about a
 * wiki edit event.
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
  private Instant timestamp;

  private String comment;

  private String server_url;

  /**
   * Custom setter for timestamp that can handle both Instant and Long values. If a Long is
   * provided, it converts it to an Instant.
   *
   * @param timestamp The timestamp value, either an Instant or a Long (milliseconds since epoch)
   */
  public void setTimestamp(Object timestamp) {
    if (timestamp instanceof Instant) {
      this.timestamp = (Instant) timestamp;
    } else if (timestamp instanceof Long) {
      this.timestamp = Instant.ofEpochMilli((Long) timestamp);
    } else if (timestamp instanceof Number) {
      this.timestamp = Instant.ofEpochMilli(((Number) timestamp).longValue());
    }
  }

  @NotBlank(message = "Wiki cannot be blank")
  private String wiki;

  private Boolean bot;

  @NotNull(message = "Meta information cannot be null")
  @Valid
  private Meta meta;

  /** Nested class representing metadata about the wiki event. */
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
