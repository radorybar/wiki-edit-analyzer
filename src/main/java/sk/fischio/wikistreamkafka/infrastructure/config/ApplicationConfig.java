package sk.fischio.wikistreamkafka.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * Centralized configuration properties for the Wiki Stream Kafka application. This class groups
 * related properties together and provides type-safe access to them.
 */
@Configuration
@ConfigurationProperties(prefix = "application")
@Getter
@Setter
public class ApplicationConfig {

  /** Configuration properties related to Kafka. */
  private final Kafka kafka = new Kafka();

  /** Configuration properties related to Wikimedia stream. */
  private final Wikimedia wikimedia = new Wikimedia();

  /** Kafka-related configuration properties. */
  @Getter
  @Setter
  public static class Kafka {
    /** The name of the Kafka topic for wiki stream events. */
    private String topicName = "wiki-stream-events";
  }

  /** Wikimedia-related configuration properties. */
  @Getter
  @Setter
  public static class Wikimedia {
    /** The URL of the Wikimedia SSE stream. */
    private String streamUrl = "https://stream.wikimedia.org/v2/stream/recentchange";
  }
}
