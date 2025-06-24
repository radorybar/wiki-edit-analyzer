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
   - `DOCKER_HUB_USERNAME`: Your Docker Hub username
   - `DOCKER_HUB_TOKEN`: Your Docker Hub access token (create one in Docker Hub account settings)
   - `REMOTE_HOST`: The hostname or IP address of your Linux server
   - `REMOTE_USER`: The username for SSH access to your server
   - `SSH_PRIVATE_KEY`: The private SSH key for authentication

2. Make sure Docker and Docker Compose are installed on your remote server.

3. Push changes to the `main` branch to trigger automatic deployment, or manually trigger the workflow from the GitHub Actions tab.

The workflow will:
- Build the Java application with Maven
- Build and push the Docker image to Docker Hub
- Deploy the application to your remote server using Docker Compose
- Set up Kafka and Zookeeper alongside your application

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
