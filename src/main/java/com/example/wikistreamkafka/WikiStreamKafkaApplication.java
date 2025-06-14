package com.example.wikistreamkafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WikiStreamKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiStreamKafkaApplication.class, args);
    }
}
