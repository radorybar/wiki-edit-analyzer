package sk.fischio.wikistreamkafka.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;

/**
 * Configuration class for WebClient. This class provides a configured WebClient bean for use
 * throughout the application.
 */
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private final ApplicationConfig applicationConfig;

  /**
   * Creates and configures a WebClient bean for the Wikimedia stream.
   *
   * @return A WebClient instance configured with the Wikimedia stream URL
   */
  @Bean
  public WebClient wikimediaWebClient() {
    return WebClient.builder().baseUrl(applicationConfig.getWikimedia().getStreamUrl()).build();
  }
}
