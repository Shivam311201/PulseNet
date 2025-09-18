# PulseNet

PulseNet is a cloud-native microservices platform that collects telemetry from multiple IoT devices, including health bands and mobile devices, and delivers automated daily summary emails to users. It efficiently handles high-frequency health data and periodic device usage while ensuring scalability and fault-tolerance.

## Key Features

- **Real-Time Health Data**: Devices push telemetry (heart rate, steps, sleep) to Kafka for low-latency processing.
- **Periodic Device Usage**: Mobile devices send usage stats every 10–15 mins via REST or Kafka.
- **Daily Summaries**: Aggregates health and device usage metrics per user and sends automated summary emails.
- **Scalable & Resilient**: Kafka for ingestion, Kubernetes for orchestration, Docker for containerization.
- **Cloud-Native Infrastructure**: Deployed on AWS EC2 / EKS, managed via Terraform and ArgoCD.
- **Monitoring & Logging**: Prometheus, Grafana, and ELK stack for observability.

## Tech Stack

- Spring Boot
- Kafka
- MySQL
- Docker
- Kubernetes
- AWS EC2 / EKS
- Terraform
- ArgoCD
- Jenkins
- Prometheus
- Grafana
- ELK Stack

## Getting Started

1. Clone the repository.
2. Deploy services using Docker Compose or Kubernetes manifests.
3. Configure Kafka topics, database connections, and SMTP/email settings.
4. Start ingestion services and daily summary scheduler.

## Future Enhancements

- Real-time email alerts for abnormal health metrics.
- Multi-region deployment for geo-resilience.
- Mobile app integration for on-demand summaries.
