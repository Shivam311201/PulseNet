# PulseNet API Service

This is a Spring Boot API service for the PulseNet project. It provides a REST API endpoint for health signal data.

## Getting Started

### Prerequisites
- Java 11
- Maven
- Docker
- Docker Compose

### Building the Application
```bash
cd api-service
mvn clean package
```

### Running the Application
```bash
java -jar target/api-service-0.0.1-SNAPSHOT.jar
```

Or using Maven:
```bash
mvn spring-boot:run
```

### Docker

#### Environment Variables
The Docker image supports the following environment variables:

**Database Configuration:**
- `SPRING_DATASOURCE_URL` - JDBC URL for the database
- `SPRING_DATASOURCE_USERNAME` - Database username
- `SPRING_DATASOURCE_PASSWORD` - Database password

**Kafka Configuration:**
- `SPRING_KAFKA_BOOTSTRAP_SERVERS` - Kafka bootstrap servers

**Email Configuration:**
- `SPRING_MAIL_USERNAME` - Email username for sending reports
- `SPRING_MAIL_PASSWORD` - Email password

**Other Configuration:**
- `SERVER_PORT` - Application server port (default: 8080)

#### Build the Docker image:
```bash
docker build -t pulsenet/api-service .
```

#### Run the Docker container with environment variables:
```bash
docker run -p 8080:8080 \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://mysql-host:3306/pulsenet_db \
  -e SPRING_DATASOURCE_USERNAME=db_user \
  -e SPRING_DATASOURCE_PASSWORD=db_password \
  -e SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
  -e SPRING_MAIL_USERNAME=your-email@example.com \
  -e SPRING_MAIL_PASSWORD=your-email-password \
  pulsenet/api-service
```

#### Using Docker Compose:
Create a `.env` file in the same directory as your `docker-compose.yml` with your environment variables:

```
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-email-password
```

Then run:
```bash
docker-compose up -d
```

## API Endpoints

- Health Signal endpoints
- User endpoints
- Device telemetry endpoints

## Health Check

- `GET /actuator/health` - Returns the health status of the application
