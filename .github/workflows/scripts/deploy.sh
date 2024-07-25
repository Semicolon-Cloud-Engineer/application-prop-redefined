#!/bin/bash
set -e

# Set up JDK
echo "Setting up JDK 17..."
# Add commands to set up JDK if necessary

# Maven Package
echo "Running Maven Package..."
mvn -B clean verify
mvn site

# Configure AWS credentials
echo "Configuring AWS credentials..."
# Add commands to configure AWS credentials if necessary

# Deploy to S3
echo "Deploying to S3..."
aws s3 sync target/site s3://${S3_BUCKET}/${PROJECT_NAME}/new-reports

# Upload artifact
echo "Uploading artifact..."
# Add commands to upload artifact if necessary

# Login to Amazon ECR
echo "Logging in to Amazon ECR..."
aws ecr get-login-password --region ${AWS_REGION} | docker login --username AWS --password-stdin ${REPO_URL}

# Build, tag, and push image to Amazon ECR
echo "Building and pushing Docker image..."
docker build -t ${REPO_URL}:latest .
docker push ${REPO_URL}:latest

# Update ECS service
echo "Updating ECS service..."
aws ecs update-service --cluster ${CLUSTER} --service ${SERVICE} --force-new-deployment
