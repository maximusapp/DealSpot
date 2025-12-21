# AWS Lambda Function for DealSpot

This Lambda function handles creating deals and saving them to DynamoDB.

## Building

```bash
./gradlew shadowJar
```

The JAR file will be created at: `build/libs/create-deal-handler.jar`

## Deployment

Upload the JAR file to AWS Lambda. See the main AWS setup documentation for details.




