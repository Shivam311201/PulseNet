# PulseNet AWS Infrastructure Setup

This directory contains configuration files to set up the complete infrastructure for PulseNet on AWS, including CI/CD pipeline with Jenkins and GitOps with ArgoCD.

## Components

1. **AWS Infrastructure** - EKS cluster, RDS MySQL, MSK (Kafka)
2. **CI/CD Pipeline** - Jenkins pipeline for building and deploying the application
3. **GitOps** - ArgoCD for Kubernetes deployment management

## Prerequisites

- AWS CLI configured with appropriate credentials
- Terraform installed
- kubectl installed
- Docker installed
- Git installed
- DockerHub account

## Setup Instructions

### 1. Set up AWS Infrastructure with Terraform

1. Initialize Terraform:

```bash
cd terraform
terraform init
```

2. Create a `terraform.tfvars` file from the example:

```bash
cp terraform.tfvars.example terraform.tfvars
```

3. Edit `terraform.tfvars` with your specific configuration values.

4. Apply the Terraform configuration:

```bash
terraform apply
```

5. After successful deployment, configure kubectl to connect to your EKS cluster:

```bash
aws eks update-kubeconfig --name pulsenet-eks-cluster --region <your-region>
```

### 2. Set up Jenkins

1. Deploy Jenkins on the EKS cluster:

```bash
kubectl create namespace jenkins
kubectl apply -f jenkins/jenkins-deployment.yaml
```

2. Get the Jenkins admin password:

```bash
kubectl exec -it $(kubectl get pods -n jenkins -l app=jenkins -o jsonpath='{.items[0].metadata.name}') -n jenkins -- cat /var/jenkins_home/secrets/initialAdminPassword
```

3. Access Jenkins UI at http://jenkins-service.jenkins.svc:8080 (use port forwarding if needed).

4. Install suggested plugins and create an admin user.

5. Configure credentials in Jenkins:
   - DockerHub credentials
   - Git credentials
   - ArgoCD credentials

6. Create a Jenkins pipeline using the provided Jenkinsfile.

### 3. Set up ArgoCD

1. Run the ArgoCD setup script:

```bash
chmod +x setup-argocd.sh
./setup-argocd.sh
```

2. Access ArgoCD UI using the credentials provided by the script.

3. Verify that the PulseNet application is configured correctly.

### 4. Push Code to GitHub

1. Push your code to GitHub:

```bash
git add .
git commit -m "Initial commit"
git push origin main
```

2. This will trigger the Jenkins pipeline, which will:
   - Build the application
   - Create a Docker image
   - Push the image to DockerHub
   - Update the Kubernetes manifests
   - Trigger ArgoCD to sync the application

## Directory Structure

- `terraform/` - Terraform configuration for AWS infrastructure
- `jenkins/` - Jenkins deployment and configuration files
- `kubernetes/` - Kubernetes manifests for the application
- `argocd/` - ArgoCD configuration

## Cleanup

To destroy all AWS resources created by Terraform:

```bash
cd terraform
terraform destroy