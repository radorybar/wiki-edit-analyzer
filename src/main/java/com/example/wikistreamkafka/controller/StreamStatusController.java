package com.example.wikistreamkafka.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
@Slf4j
public class StreamStatusController {

    @GetMapping
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Wiki Stream to Kafka service is running");
    }
}
