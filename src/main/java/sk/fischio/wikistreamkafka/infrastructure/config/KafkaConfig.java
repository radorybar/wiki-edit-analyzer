package sk.fischio.wikistreamkafka.infrastructure.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import lombok.RequiredArgsConstructor;

/** Configuration class for Kafka. This class provides Kafka-related beans and configuration. */
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

  private final ApplicationConfig applicationConfig;

  /**
   * Creates a Kafka topic for wiki stream events.
   *
   * @return A NewTopic instance configured with the topic name from application properties
   */
  @Bean
  public NewTopic wikiTopic() {
    return TopicBuilder.name(applicationConfig.getKafka().getTopicName())
        .partitions(3)
        .replicas(1)
        .build();
  }
}
