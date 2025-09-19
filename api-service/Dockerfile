FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/api-service-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run the Spring Boot application with environment variables
ENTRYPOINT ["java", \
  "-jar", "app.jar", \
  "--spring.datasource.url=${SPRING_DATASOURCE_URL}", \
  "--spring.datasource.username=${SPRING_DATASOURCE_USERNAME}", \
  "--spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}", \
  "--spring.kafka.bootstrap-servers=${SPRING_KAFKA_BOOTSTRAP_SERVERS}", \
  "--spring.mail.username=${SPRING_MAIL_USERNAME}", \
  "--spring.mail.password=${SPRING_MAIL_PASSWORD}", \
  "--server.port=${SERVER_PORT}"]
