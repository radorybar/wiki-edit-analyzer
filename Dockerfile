FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for health checks
RUN apk --no-cache add curl

# Add application jar file (assumes 'mvn package' was run before building the image)
COPY target/*.jar app.jar

# Create volume for logs
VOLUME /app/logs

# Set environment variables with defaults that can be overridden
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092
ENV SERVER_PORT=8080

EXPOSE 8080

# Add health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:${SERVER_PORT}/actuator/health || exit 1

ENTRYPOINT ["java", "-jar", "/app/app.jar"]