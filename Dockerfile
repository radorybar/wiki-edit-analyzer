FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Add application jar file
COPY target/*.jar app.jar

# Create volume for logs
VOLUME /app/logs

# Set environment variables with defaults that can be overridden
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
