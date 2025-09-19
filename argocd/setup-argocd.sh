#!/bin/bash
# Script to install and configure ArgoCD on the Kubernetes cluster

# Create argocd namespace
kubectl create namespace argocd

# Apply ArgoCD manifests
kubectl apply -n argocd -f https://raw.githubusercontent.com/argoproj/argo-cd/stable/manifests/install.yaml

# Wait for ArgoCD server to be running
echo "Waiting for ArgoCD server to start..."
kubectl wait --for=condition=available --timeout=300s deployment/argocd-server -n argocd

# Get the ArgoCD admin password
ARGOCD_ADMIN_PASSWORD=$(kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d)
echo "ArgoCD admin password: $ARGOCD_ADMIN_PASSWORD"

# Set up port forwarding to access ArgoCD API server
kubectl port-forward svc/argocd-server -n argocd 8080:443 &
PF_PID=$!

# Wait for port forwarding to be established
sleep 5

# Install ArgoCD CLI
if ! command -v argocd &> /dev/null; then
    echo "Installing ArgoCD CLI..."
    curl -sSL -o argocd-linux-amd64 https://github.com/argoproj/argo-cd/releases/latest/download/argocd-linux-amd64
    sudo install -m 555 argocd-linux-amd64 /usr/local/bin/argocd
    rm argocd-linux-amd64
fi

# Login to ArgoCD
echo "Logging in to ArgoCD..."
argocd login localhost:8080 --username admin --password $ARGOCD_ADMIN_PASSWORD --insecure

# Create the PulseNet application
echo "Creating PulseNet application in ArgoCD..."
kubectl apply -f argocd-application.yaml

# Stop port forwarding
kill $PF_PID

echo "ArgoCD setup completed!"
echo "Access the ArgoCD UI at: https://localhost:8080"
echo "Username: admin"
echo "Password: $ARGOCD_ADMIN_PASSWORD"