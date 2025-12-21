package com.dealspot.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.google.gson.Gson
import software.amazon.awssdk.services.dynamodb.DynamoDbClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest
import java.util.*

data class CreateDealRequest(
    val name: String,
    val description: String,
    val categoryId: Long,
    val categoryName: String,
    val serviceId: Long,
    val serviceName: String,
    val latitude: Double,
    val longitude: Double,
    val isUrgent: Boolean,
    val dateTime: String
)

data class CreateDealResponse(
    val success: Boolean,
    val message: String,
    val dealId: String? = null
)

class CreateDealHandler : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private val gson = Gson()
    private val dynamoDbClient = DynamoDbClient.builder().build()
    private val tableName = System.getenv("DEALS_TABLE_NAME") ?: "DealSpot-Deals"
    
    override fun handleRequest(
        input: APIGatewayProxyRequestEvent,
        context: Context?
    ): APIGatewayProxyResponseEvent {
        return try {
            context?.logger?.log("Received request: ${input.body}")
            
            val request = gson.fromJson(input.body, CreateDealRequest::class.java)
            
            // Validate request
            if (request.name.isBlank() || request.description.isBlank()) {
                return createResponse(400, CreateDealResponse(
                    success = false,
                    message = "Name and description are required"
                ))
            }
            
            // Generate unique deal ID
            val dealId = UUID.randomUUID().toString()
            
            // Save to DynamoDB
            val item = mapOf(
                "dealId" to AttributeValue.builder().s(dealId).build(),
                "name" to AttributeValue.builder().s(request.name).build(),
                "description" to AttributeValue.builder().s(request.description).build(),
                "categoryId" to AttributeValue.builder().n(request.categoryId.toString()).build(),
                "categoryName" to AttributeValue.builder().s(request.categoryName).build(),
                "serviceId" to AttributeValue.builder().n(request.serviceId.toString()).build(),
                "serviceName" to AttributeValue.builder().s(request.serviceName).build(),
                "latitude" to AttributeValue.builder().n(request.latitude.toString()).build(),
                "longitude" to AttributeValue.builder().n(request.longitude.toString()).build(),
                "isUrgent" to AttributeValue.builder().bool(request.isUrgent).build(),
                "dateTime" to AttributeValue.builder().s(request.dateTime).build(),
                "createdAt" to AttributeValue.builder().s(System.currentTimeMillis().toString()).build()
            )
            
            val putItemRequest = PutItemRequest.builder()
                .tableName(tableName)
                .item(item)
                .build()
            
            dynamoDbClient.putItem(putItemRequest)
            
            context?.logger?.log("Deal saved successfully with ID: $dealId")
            
            createResponse(200, CreateDealResponse(
                success = true,
                message = "Deal created successfully",
                dealId = dealId
            ))
            
        } catch (e: Exception) {
            context?.logger?.log("Error processing request: ${e.message}")
            e.printStackTrace()
            
            createResponse(500, CreateDealResponse(
                success = false,
                message = "Internal server error: ${e.message}"
            ))
        }
    }
    
    private fun createResponse(statusCode: Int, body: CreateDealResponse): APIGatewayProxyResponseEvent {
        return APIGatewayProxyResponseEvent()
            .withStatusCode(statusCode)
            .withHeaders(mapOf(
                "Content-Type" to "application/json",
                "Access-Control-Allow-Origin" to "*",
                "Access-Control-Allow-Headers" to "Content-Type",
                "Access-Control-Allow-Methods" to "POST,OPTIONS"
            ))
            .withBody(gson.toJson(body))
    }
}




