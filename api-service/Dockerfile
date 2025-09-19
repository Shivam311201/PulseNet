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
  "--spring.jpa.hibernate.ddl-auto=${SPRING_JPA_HIBERNATE_DDL_AUTO:update}", \
  "--spring.jpa.show-sql=${SPRING_JPA_SHOW_SQL:true}", \
  "--spring.jpa.properties.hibernate.dialect=${SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT:org.hibernate.dialect.MySQL5Dialect}", \
  "--spring.kafka.bootstrap-servers=${SPRING_KAFKA_BOOTSTRAP_SERVERS}", \
  "--spring.kafka.consumer.group-id=${SPRING_KAFKA_CONSUMER_GROUP_ID:pulsenet-health-consumer-v4}", \
  "--spring.kafka.consumer.auto-offset-reset=${SPRING_KAFKA_CONSUMER_AUTO_OFFSET_RESET:latest}", \
  "--spring.kafka.consumer.key-deserializer=org.apache.kafka.common.serialization.StringDeserializer", \
  "--spring.kafka.consumer.value-deserializer=org.springframework.kafka.support.serializer.JsonDeserializer", \
  "--spring.kafka.consumer.properties.spring.json.trusted.packages=*", \
  "--spring.kafka.consumer.properties.spring.json.use.type.headers=false", \
  "--kafka.topic.user-health-signals=${KAFKA_TOPIC_USER_HEALTH_SIGNALS:user-health-signals}", \
  "--spring.mail.host=${SPRING_MAIL_HOST:smtp.gmail.com}", \
  "--spring.mail.port=${SPRING_MAIL_PORT:587}", \
  "--spring.mail.username=${SPRING_MAIL_USERNAME}", \
  "--spring.mail.password=${SPRING_MAIL_PASSWORD}", \
  "--spring.mail.properties.mail.smtp.auth=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH:true}", \
  "--spring.mail.properties.mail.smtp.starttls.enable=${SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE:true}", \
  "--report.scheduler.cron=${REPORT_SCHEDULER_CRON:0 0 6 * * *}", \
  "--report.sender.from-email=${REPORT_SENDER_FROM_EMAIL:no-reply@pulsenet.com}", \
  "--report.sender.from-name=${REPORT_SENDER_FROM_NAME:PulseNet Daily Report}", \
  "--management.endpoints.web.exposure.include=${MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE:health,info,metrics}", \
  "--management.endpoint.health.show-details=${MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS:always}", \
  "--server.port=${SERVER_PORT:8080}", \
  "--spring.application.name=${SPRING_APPLICATION_NAME:pulsenet-api-service}"]
