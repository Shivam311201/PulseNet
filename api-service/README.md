# PulseNet API Service

This is a simple Spring Boot API service for the PulseNet project. It provides a basic REST API endpoint.

## Getting Started

### Prerequisites
- Java 11
- Maven

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
Build the Docker image:
```bash
docker build -t pulsenet/api-service .
```

Run the Docker container:
```bash
docker run -p 8080:8080 pulsenet/api-service
```

## API Endpoints

- `GET /api/hello` - Returns a hello message

## Health Check

- `GET /actuator/health` - Returns the health status of the application
