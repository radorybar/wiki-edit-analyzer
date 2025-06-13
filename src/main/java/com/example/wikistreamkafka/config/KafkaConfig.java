package com.example.wikistreamkafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    
    @Value("${kafka.topic.wiki-stream}")
    private String wikiStreamTopic;
    
    @Bean
    public NewTopic wikiTopic() {
        return TopicBuilder.name(wikiStreamTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
