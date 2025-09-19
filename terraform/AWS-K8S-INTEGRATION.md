# Using PulseNet with AWS Resources

This guide explains how to configure your Kubernetes deployment to use AWS resources created with Terraform.

## Prerequisites

- AWS CLI configured with appropriate permissions
- Terraform installed and initialized in the project directory
- kubectl configured to connect to your EKS cluster
- Infrastructure deployed using the provided Terraform configuration

## Getting AWS Resource Information

After deploying the infrastructure with Terraform, you can get the necessary information about your AWS resources using:

```bash
# Get MSK bootstrap servers
terraform output msk_bootstrap_brokers

# Get RDS endpoint
terraform output rds_endpoint

# Get database credentials (if configured to output them)
terraform output db_username
terraform output db_password
```

## Updating Kubernetes Configurations

### Automatic Update

Run the provided script to automatically update your Kubernetes configurations with AWS resource information:

```bash
# Make the script executable
chmod +x update-k8s-aws-config.sh

# Run the script
./update-k8s-aws-config.sh
```

### Manual Update

If you prefer to update the configurations manually:

1. Edit `kubernetes/k8s-configmap.yaml`:
   - Replace `${MSK_BOOTSTRAP_SERVERS}` with the value from `terraform output msk_bootstrap_brokers`

2. Edit `kubernetes/k8s-secret.yaml`:
   - Replace `${RDS_ENDPOINT}` with the value from `terraform output rds_endpoint`
   - Replace `${DB_USERNAME}` and `${DB_PASSWORD}` with the appropriate values
   - Update email credentials as needed

3. Apply the configurations:
   ```bash
   kubectl apply -f kubernetes/k8s-configmap.yaml
   kubectl apply -f kubernetes/k8s-secret.yaml
   ```

## Verifying the Configuration

After applying the configurations, you can verify that they are correctly set up:

```bash
# Check ConfigMap
kubectl get configmap pulsenet-api-config -o yaml

# Check Secret (values will be base64 encoded)
kubectl get secret pulsenet-api-secrets -o yaml

# Verify that your pods can access the AWS resources
kubectl logs deployment/pulsenet-api-service
```

## Troubleshooting

If your application cannot connect to AWS resources:

1. Verify security groups allow access from EKS to RDS and MSK
2. Check that the endpoints are correctly formatted in the ConfigMap and Secret
3. Ensure that the EKS nodes have IAM permissions to access MSK and RDS if needed
4. Check application logs for specific connection errors