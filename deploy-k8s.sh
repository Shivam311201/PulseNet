#!/bin/bash

# Create Kubernetes resources in the right order

echo "Creating ConfigMap and Secret..."
kubectl apply -f k8s-configmap.yaml
kubectl apply -f k8s-secret.yaml

echo "Deploying MySQL database..."
kubectl apply -f k8s-mysql.yaml

echo "Deploying Kafka and Zookeeper..."
kubectl apply -f k8s-kafka.yaml

echo "Waiting for database and Kafka to be ready..."
sleep 15

echo "Deploying API Service..."
kubectl apply -f k8s-api-service.yaml

echo "All resources deployed successfully!"
echo "You can access the API at: http://pulsenet.local (Add pulsenet.local to your hosts file)"
echo "To check the status of the deployments, run: kubectl get pods"