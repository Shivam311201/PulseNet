#!/bin/bash
# Script to update Kubernetes ConfigMap and Secret with AWS resource information from Terraform outputs

# Set variables from Terraform outputs
export MSK_BOOTSTRAP_SERVERS=$(terraform -chdir=. output -raw msk_bootstrap_brokers)
export RDS_ENDPOINT=$(terraform -chdir=. output -raw rds_endpoint)
export DB_USERNAME=$(terraform -chdir=. output -raw db_username || echo "pulsenet_admin")
export DB_PASSWORD=$(terraform -chdir=. output -raw db_password || echo "please-change-me")

echo "Retrieved Terraform outputs:"
echo "MSK Bootstrap Servers: $MSK_BOOTSTRAP_SERVERS"
echo "RDS Endpoint: $RDS_ENDPOINT"

# Create temporary files with substituted values
cat > kubernetes/k8s-configmap.yaml << EOF
apiVersion: v1
kind: ConfigMap
metadata:
  name: pulsenet-api-config
  labels:
    app: pulsenet
data:
  # Server configuration
  SERVER_PORT: "8080"
  SPRING_APPLICATION_NAME: "pulsenet-api-service"

  # Kafka configuration - Using AWS MSK
  SPRING_KAFKA_BOOTSTRAP_SERVERS: "${MSK_BOOTSTRAP_SERVERS}"
  KAFKA_TOPIC_USER_HEALTH_SIGNALS: "user-health-signals"
  
  # JPA configuration
  SPRING_JPA_HIBERNATE_DDL_AUTO: "update"
  SPRING_JPA_SHOW_SQL: "true"
  SPRING_JPA_PROPERTIES_HIBERNATE_DIALECT: "org.hibernate.dialect.MySQL5Dialect"
  
  # Report configuration
  REPORT_SCHEDULER_CRON: "0 0 6 * * *"
  REPORT_SENDER_FROM_EMAIL: "no-reply@pulsenet.com"
  REPORT_SENDER_FROM_NAME: "PulseNet Daily Report"
  
  # Actuator configuration
  MANAGEMENT_ENDPOINTS_WEB_EXPOSURE_INCLUDE: "health,info,metrics"
  MANAGEMENT_ENDPOINT_HEALTH_SHOW_DETAILS: "always"
EOF

cat > kubernetes/k8s-secret.yaml << EOF
apiVersion: v1
kind: Secret
metadata:
  name: pulsenet-api-secrets
  labels:
    app: pulsenet
type: Opaque
stringData:
  # Database configuration - Using AWS RDS
  SPRING_DATASOURCE_URL: "jdbc:mysql://${RDS_ENDPOINT}/pulsenet_db?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true"
  SPRING_DATASOURCE_USERNAME: "${DB_USERNAME}"
  SPRING_DATASOURCE_PASSWORD: "${DB_PASSWORD}"
  
  # Email configuration
  SPRING_MAIL_HOST: "smtp.gmail.com"
  SPRING_MAIL_PORT: "587"
  SPRING_MAIL_USERNAME: "your-email@gmail.com"
  SPRING_MAIL_PASSWORD: "your-app-password"
  SPRING_MAIL_PROPERTIES_MAIL_SMTP_AUTH: "true"
  SPRING_MAIL_PROPERTIES_MAIL_SMTP_STARTTLS_ENABLE: "true"
EOF

echo "Created Kubernetes configuration files with AWS resource information."

# Apply the Kubernetes configurations
echo "Applying Kubernetes configurations..."

# Create namespace if it doesn't exist
kubectl create namespace pulsenet --dry-run=client -o yaml | kubectl apply -f -

# Apply configurations
kubectl apply -f kubernetes/k8s-configmap.yaml -n pulsenet
kubectl apply -f kubernetes/k8s-secret.yaml -n pulsenet
kubectl apply -f kubernetes/k8s-api-service.yaml -n pulsenet

echo "Kubernetes configurations applied successfully!"
echo "You can now verify the deployment with: kubectl get pods -n pulsenet"