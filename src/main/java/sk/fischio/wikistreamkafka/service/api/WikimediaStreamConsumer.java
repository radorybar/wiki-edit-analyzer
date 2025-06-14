package sk.fischio.wikistreamkafka.service.api;

/**
 * Service interface for consuming the Wikimedia stream and publishing events to Kafka. This
 * interface defines methods for starting and managing the stream consumption.
 */
public interface WikimediaStreamConsumer {

  /**
   * Starts consuming the Wikimedia stream and publishing events to Kafka. This method connects to
   * the Wikimedia SSE stream, processes the incoming data, and sends it to Kafka for further
   * processing.
   */
  void consumeStreamAndPublishToKafka();
}
