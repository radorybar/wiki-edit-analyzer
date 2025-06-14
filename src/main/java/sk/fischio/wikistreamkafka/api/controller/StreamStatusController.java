package sk.fischio.wikistreamkafka.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/** REST controller for providing status information about the Wiki Stream service. */
@RestController
@RequestMapping("/api/status")
@Slf4j
public class StreamStatusController {

  /**
   * Returns the current status of the Wiki Stream service.
   *
   * @return A response entity with a status message
   */
  @GetMapping
  public ResponseEntity<String> getStatus() {
    return ResponseEntity.ok("Wiki Stream to Kafka service is running");
  }
}
