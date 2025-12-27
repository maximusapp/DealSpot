# AWS Backend Setup Guide for DealSpot

This guide will walk you through setting up the AWS backend infrastructure for DealSpot, including DynamoDB, Lambda functions, and API Gateway.

## Architecture Overview

- **DynamoDB Table**: Stores deal data
- **Lambda Function (Kotlin)**: Processes deal creation requests
- **API Gateway (REST)**: Exposes the Lambda function as a REST API
- **IAM Roles**: Provides necessary permissions

## Prerequisites

- AWS Account
- AWS CLI installed and configured
- AWS Console access
- Basic knowledge of AWS services

---

## Step 1: Create DynamoDB Table

### 1.1 Using AWS Console

1. Go to **DynamoDB** in AWS Console
2. Click **Create table**
3. Configure:
   - **Table name**: `DealSpot-Deals`
   - **Partition key**: `dealId` (String)
   - **Table settings**: Use default settings
   - **Capacity mode**: On-demand (recommended for development)
4. Click **Create table**

### 1.2 Using AWS CLI

```bash
aws dynamodb create-table \
    --table-name DealSpot-Deals \
    --attribute-definitions AttributeName=dealId,AttributeType=S \
    --key-schema AttributeName=dealId,KeyType=HASH \
    --billing-mode PAY_PER_REQUEST \
    --region eu-west-1
```

### 1.3 Verify Table Creation

```bash
aws dynamodb describe-table --table-name DealSpot-Deals --region eu-west-1
```

---

## Step 2: Create IAM Role for Lambda

### 2.1 Create Trust Policy

Create a file `lambda-trust-policy.json`:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
      "Action": "sts:AssumeRole"
    }
  ]
}
```

### 2.2 Create IAM Role

```bash
aws iam create-role \
    --role-name DealSpot-LambdaRole \
    --assume-role-policy-document file://lambda-trust-policy.json \
    --region eu-west-1
```

### 2.3 Attach Policies

Attach the basic Lambda execution policy and DynamoDB access:

```bash
# Basic Lambda execution
aws iam attach-role-policy \
    --role-name DealSpot-LambdaRole \
    --policy-arn arn:aws:iam::aws:policy/service-role/AWSLambdaBasicExecutionRole

# DynamoDB access (create a custom policy)
```

### 2.4 Create Custom DynamoDB Policy

Create `dynamodb-policy.json`:

```json
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": [
        "dynamodb:PutItem",
        "dynamodb:GetItem",
        "dynamodb:UpdateItem",
        "dynamodb:DeleteItem",
        "dynamodb:Query",
        "dynamodb:Scan"
      ],
      "Resource": "arn:aws:dynamodb:eu-west-1:YOUR_ACCOUNT_ID:table/DealSpot-Deals"
    }
  ]
}
```

Replace `YOUR_ACCOUNT_ID` with your AWS account ID.

```bash
aws iam create-policy \
    --policy-name DealSpot-DynamoDBPolicy \
    --policy-document file://dynamodb-policy.json

# Get the policy ARN from the output, then attach it
aws iam attach-role-policy \
    --role-name DealSpot-LambdaRole \
    --policy-arn arn:aws:iam::YOUR_ACCOUNT_ID:policy/DealSpot-DynamoDBPolicy
```

---

## Step 3: Build Lambda Function

### 3.1 Navigate to Lambda Directory

```bash
cd aws-lambda
```

### 3.2 Build the JAR

```bash
./gradlew shadowJar
```

This creates: `build/libs/create-deal-handler.jar`

---

## Step 4: Create Lambda Function

### 4.1 Using AWS Console

1. Go to **Lambda** in AWS Console
2. Click **Create function**
3. Choose **Author from scratch**
4. Configure:
   - **Function name**: `DealSpot-CreateDeal`
   - **Runtime**: Java 17 (or Java 11)
   - **Architecture**: x86_64
   - **Execution role**: Use existing role → `DealSpot-LambdaRole`
5. Click **Create function**

### 4.2 Upload Code

1. In the function page, scroll to **Code source**
2. Click **Upload from** → **.zip or .jar file**
3. Upload `build/libs/create-deal-handler.jar`
4. Set **Handler**: `com.dealspot.lambda.CreateDealHandler::handleRequest`

### 4.3 Configure Environment Variables

1. Go to **Configuration** → **Environment variables**
2. Add:
   - Key: `DEALS_TABLE_NAME`
   - Value: `DealSpot-Deals`

### 4.4 Configure Timeout and Memory

1. Go to **Configuration** → **General configuration**
2. Click **Edit**
3. Set:
   - **Timeout**: 30 seconds
   - **Memory**: 512 MB

### 4.5 Using AWS CLI

```bash
aws lambda create-function \
    --function-name DealSpot-CreateDeal \
    --runtime java17 \
    --role arn:aws:iam::YOUR_ACCOUNT_ID:role/DealSpot-LambdaRole \
    --handler com.dealspot.lambda.CreateDealHandler::handleRequest \
    --zip-file fileb://build/libs/create-deal-handler.jar \
    --timeout 30 \
    --memory-size 512 \
    --environment Variables={DEALS_TABLE_NAME=DealSpot-Deals} \
    --region eu-west-1
```

---

## Step 5: Create API Gateway REST API

### 5.1 Using AWS Console

1. Go to **API Gateway** in AWS Console
2. Click **Create API**
3. Choose **REST API** → **Build**
4. Configure:
   - **Protocol**: REST
   - **Create new API**: New API
   - **API name**: `DealSpot-API`
   - **Endpoint Type**: Regional
5. Click **Create API**

### 5.2 Create Resource

1. In the API, click **Actions** → **Create Resource**
2. Configure:
   - **Resource Name**: `deals`
   - **Resource Path**: `deals`
3. Click **Create Resource**

### 5.3 Create POST Method

1. Select the `/deals` resource
2. Click **Actions** → **Create Method**
3. Choose **POST**
4. Configure:
   - **Integration type**: Lambda Function
   - **Lambda Region**: eu-west-1
   - **Lambda Function**: DealSpot-CreateDeal
   - **Use Lambda Proxy integration**: ✅ Checked
5. Click **Save** → **OK** (when prompted to give API Gateway permission)

### 5.4 Enable CORS

1. Select the `/deals` resource
2. Click **Actions** → **Enable CORS**
3. Configure:
   - **Access-Control-Allow-Origin**: `*` (or your app's domain)
   - **Access-Control-Allow-Headers**: `Content-Type`
   - **Access-Control-Allow-Methods**: `POST,OPTIONS`
4. Click **Enable CORS and replace existing CORS headers**

### 5.5 Deploy API

1. Click **Actions** → **Deploy API**
2. Configure:
   - **Deployment stage**: `prod` (or create new stage)
   - **Stage description**: Production deployment
3. Click **Deploy**
4. **Note the Invoke URL** (e.g., `https://abc123.execute-api.eu-west-1.amazonaws.com/prod`)


## Step 6: Update App Configuration

### 6.1 Update API Gateway URL

In your app, update `AWSConfig.kt`:

```kotlin
const val API_GATEWAY_URL = "https://YOUR_API_ID.execute-api.eu-west-1.amazonaws.com/prod"
```

Replace `YOUR_API_ID` with your actual API Gateway ID.

---

## Step 7: Test the Setup

### 7.1 Test Lambda Function

```bash
aws lambda invoke \
    --function-name DealSpot-CreateDeal \
    --payload '{"body":"{\"name\":\"Test Deal\",\"description\":\"Test Description\",\"categoryId\":1,\"categoryName\":\"Test Category\",\"serviceId\":1,\"serviceName\":\"Test Service\",\"latitude\":50.0,\"longitude\":30.0,\"isUrgent\":false,\"dateTime\":\"2024-01-15 12:00:00\"}"}' \
    --region eu-west-1 \
    response.json

cat response.json
```

### 7.2 Test API Gateway

```bash
curl -X POST https://YOUR_API_ID.execute-api.eu-west-1.amazonaws.com/prod/deals \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test Deal",
    "description": "Test Description",
    "categoryId": 1,
    "categoryName": "Test Category",
    "serviceId": 1,
    "serviceName": "Test Service",
    "latitude": 50.0,
    "longitude": 30.0,
    "isUrgent": false,
    "dateTime": "2024-01-15 12:00:00"
  }'
```

### 7.3 Verify DynamoDB

```bash
aws dynamodb scan --table-name DealSpot-Deals --region eu-west-1
```

---

## Step 8: Security Considerations (Optional but Recommended)

### 8.1 Add API Key (Optional)

1. In API Gateway, go to **API Keys**
2. Create a new API key
3. Create a usage plan and associate it with your API

### 8.2 Add Cognito Authorizer (Recommended)

If you want to authenticate requests using Cognito:

1. In API Gateway, go to **Authorizers**
2. Create a new **Cognito User Pool authorizer**
3. Select your Cognito User Pool
4. Attach the authorizer to your POST method

### 8.3 Update Lambda to Extract User Info

Modify the Lambda function to extract user information from the Cognito token if using authorizer.

---

## Troubleshooting

### Lambda Function Not Working

- Check CloudWatch Logs for errors
- Verify IAM role has correct permissions
- Check environment variables are set correctly

### API Gateway Returns 500

- Check Lambda function logs in CloudWatch
- Verify Lambda function is deployed correctly
- Check API Gateway integration settings

### DynamoDB Access Denied

- Verify IAM role has DynamoDB permissions
- Check table name matches environment variable
- Verify table exists in the correct region

---

## Cost Estimation

- **DynamoDB**: On-demand pricing - ~$1.25 per million write requests
- **Lambda**: First 1M requests free, then $0.20 per 1M requests
- **API Gateway**: $3.50 per million API calls

For development/testing, costs should be minimal.

---

## Next Steps

1. Add error handling and validation
2. Implement authentication/authorization
3. Add more endpoints (GET deals, UPDATE deal, DELETE deal)
4. Set up CloudWatch alarms for monitoring
5. Implement API versioning
6. Add request/response logging

---

## Additional Resources

- [AWS Lambda Kotlin Guide](https://docs.aws.amazon.com/lambda/latest/dg/lambda-kotlin.html)
- [DynamoDB Best Practices](https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/best-practices.html)
- [API Gateway REST API](https://docs.aws.amazon.com/apigateway/latest/developerguide/apigateway-rest-api.html)




