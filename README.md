# Wiki Stream to Kafka Service

This Spring Boot application consumes the Server-Sent Events (SSE) stream from Wikimedia's recent changes API and forwards these events to a Kafka topic.

## Features

- Connects to Wikimedia's SSE stream at `https://stream.wikimedia.org/v2/stream/recentchange`
- Processes and forwards events to a Kafka topic in real-time
- Uses reactive programming for non-blocking I/O operations

## Prerequisites

- Java 21
- Apache Kafka (running locally on port 9092 by default)
- Maven

## Running the Application

### Start Kafka

Before running the application, make sure Kafka is running. You can use Docker for a quick setup:
