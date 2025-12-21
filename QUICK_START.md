# Quick Start Guide - DealSpot AWS Integration

## What Was Created

### Mobile App (KMM)
1. **Data Models**:
   - `CreateDealRequest.kt` - Request model for creating deals
   - `CreateDealResponse.kt` - Response model from API

2. **Repository Layer**:
   - `DealRepository.kt` - Interface for deal operations
   - `DealRepositoryImpl.kt` - Implementation using Ktor client (multiplatform)
   - Platform-specific implementations for Android and iOS

3. **ViewModel Update**:
   - `LookingForServiceViewModel.kt` - Updated to use `DealRepository` for publishing deals

4. **Configuration**:
   - `AWSConfig.kt` - Added `API_GATEWAY_URL` constant (update with your API Gateway URL)

### AWS Backend
1. **Lambda Function**:
   - `CreateDealHandler.kt` - Main handler using AWS SDK v2
   - `CreateDealHandlerV1.kt` - Alternative using AWS SDK v1
   - `build.gradle.kts` - Build configuration

2. **Documentation**:
   - `AWS_SETUP.md` - Complete step-by-step AWS setup guide

## Next Steps

### 1. Set Up AWS Backend
Follow the detailed instructions in `AWS_SETUP.md` to:
- Create DynamoDB table
- Create Lambda function
- Set up API Gateway
- Configure IAM roles

### 2. Update API Gateway URL
After deploying API Gateway, update `AWSConfig.kt`:

```kotlin
const val API_GATEWAY_URL = "https://YOUR_API_ID.execute-api.eu-west-1.amazonaws.com/prod"
```

### 3. Test the Integration
1. Run your app
2. Fill in the deal form
3. Call `publishDeal()` from `LookingForServiceViewModel`
4. Check CloudWatch logs and DynamoDB table to verify

## Architecture Flow

```
Mobile App (KMM)
    ↓
LookingForServiceViewModel.publishDeal()
    ↓
DealRepository.createDeal()
    ↓
Ktor HTTP Client
    ↓
API Gateway (REST)
    ↓
Lambda Function (Kotlin)
    ↓
DynamoDB Table
```

## Testing

### Test from App
The `publishDeal()` function will:
1. Validate required fields (location, category, service)
2. Create `CreateDealRequest` with current date/time
3. Send POST request to API Gateway
4. Log success/error responses

### Test API Directly
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

## Troubleshooting

### App Can't Connect to API
- Verify `API_GATEWAY_URL` is correct in `AWSConfig.kt`
- Check API Gateway is deployed and accessible
- Verify CORS is enabled in API Gateway

### Lambda Function Errors
- Check CloudWatch Logs for the Lambda function
- Verify DynamoDB table name matches environment variable
- Check IAM role has DynamoDB permissions

### Data Not Saving
- Verify DynamoDB table exists
- Check Lambda function logs
- Verify request format matches `CreateDealRequest` model

## Files Modified/Created

### Created:
- `composeApp/src/commonMain/kotlin/com/app/dealspot/data/model/CreateDealRequest.kt`
- `composeApp/src/commonMain/kotlin/com/app/dealspot/data/model/CreateDealResponse.kt`
- `composeApp/src/commonMain/kotlin/com/app/dealspot/data/DealRepository.kt`
- `composeApp/src/commonMain/kotlin/com/app/dealspot/data/DealRepositoryImpl.kt`
- `composeApp/src/androidMain/kotlin/com/app/dealspot/data/DealRepositoryImpl.kt`
- `composeApp/src/iosMain/kotlin/com/app/dealspot/data/DealRepositoryImpl.kt`
- `aws-lambda/src/main/kotlin/com/dealspot/lambda/CreateDealHandler.kt`
- `aws-lambda/src/main/kotlin/com/dealspot/lambda/CreateDealHandlerV1.kt`
- `aws-lambda/build.gradle.kts`
- `AWS_SETUP.md`
- `QUICK_START.md`

### Modified:
- `composeApp/src/commonMain/kotlin/com/app/dealspot/presentation/ui/home/search_provide_for_service/looking_for_service/LookingForServiceViewModel.kt`
- `composeApp/src/commonMain/kotlin/com/app/dealspot/di/Koin.kt`
- `composeApp/src/commonMain/kotlin/com/app/dealspot/common/AWSConfig.kt`




