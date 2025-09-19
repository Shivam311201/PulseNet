# PulseNet Kubernetes Deployment

This directory contains Kubernetes manifests for deploying the PulseNet application stack.

## Components

1. **API Service** - Spring Boot application for handling health signal data
2. **MySQL Database** - Persistent storage for user and health data
3. **Kafka & Zookeeper** - Message broker for real-time health data processing

## Prerequisites

- Kubernetes cluster (local or cloud-based)
- kubectl configured to work with your cluster
- Docker to build the API service image

## Configuration

Before deploying, you should update the following configuration files:

1. **k8s-secret.yaml**: Update with your actual database credentials and email settings
2. **k8s-configmap.yaml**: Adjust any application configuration as needed

## Building the API Service Docker Image

```bash
cd api-service
mvn clean package
docker build -t pulsenet/api-service:latest .
```

If using a remote Kubernetes cluster, push the image to a container registry:
```bash
docker tag pulsenet/api-service:latest <your-registry>/pulsenet/api-service:latest
docker push <your-registry>/pulsenet/api-service:latest
```

Then update the image reference in `k8s-api-service.yaml`.

## Deployment

You can deploy all components at once using the provided script:

```bash
chmod +x deploy-k8s.sh
./deploy-k8s.sh
```

Or deploy each component manually:

```bash
kubectl apply -f k8s-configmap.yaml
kubectl apply -f k8s-secret.yaml
kubectl apply -f k8s-mysql.yaml
kubectl apply -f k8s-kafka.yaml
kubectl apply -f k8s-api-service.yaml
```

## Accessing the Application

The API service is exposed through an Ingress at `pulsenet.local`. Add this to your hosts file:

```
127.0.0.1 pulsenet.local
```

If you're using Minikube, you may need to enable the Ingress addon:

```bash
minikube addons enable ingress
```

## Checking Status

```bash
# Get all pods
kubectl get pods

# Get all services
kubectl get services

# Check logs for the API service
kubectl logs deployment/pulsenet-api-service

# Port forward to access the API directly
kubectl port-forward svc/pulsenet-api-service 8080:8080
```

## Clean Up

To remove all resources:

```bash
kubectl delete -f k8s-api-service.yaml
kubectl delete -f k8s-kafka.yaml
kubectl delete -f k8s-mysql.yaml
kubectl delete -f k8s-secret.yaml
kubectl delete -f k8s-configmap.yaml
```