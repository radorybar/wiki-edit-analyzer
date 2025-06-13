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
- Docker and Docker Compose (for containerized deployment)

## Running the Application Locally

### Start Kafka

Before running the application, make sure Kafka is running. You can use Docker Compose for a quick setup:

```bash
docker-compose up -d zookeeper kafka
```

### Build and Run the Application

```bash
mvn clean package
java -jar target/wiki-stream-kafka-0.0.1-SNAPSHOT.jar
```

## Deployment

### Using GitHub Actions

This project includes a GitHub Actions workflow for automated build and deployment to a Linux server.

1. Set up the following secrets in your GitHub repository:
   - `SSH_HOST`: The hostname or IP address of your Linux server
   - `SSH_USERNAME`: The username for SSH access
   - `SSH_KEY`: The private SSH key for authentication
   - `SSH_PORT`: (Optional) The SSH port number if not using the default port 22

2. Push changes to the `main` branch to trigger automatic deployment

### Manual Deployment

1. Build the Docker image:
   ```bash
   mvn clean package
   docker build -t wiki-stream-kafka:latest .
   ```

2. Deploy using Docker Compose:
   ```bash
   docker-compose up -d
   ```

## Application Status

You can check if the application is running by accessing the status endpoint:

```
GET http://localhost:8080/api/status
```
