package com.example.wikistreamkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private final ApplicationConfig applicationConfig;

    @Bean
    public NewTopic wikiTopic() {
        return TopicBuilder.name(applicationConfig.getKafka().getTopicName())
                .partitions(3)
                .replicas(1)
                .build();
    }
}
