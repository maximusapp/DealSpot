package com.dealspot.lambda

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import com.amazonaws.services.dynamodbv2.document.Item
import com.amazonaws.services.dynamodbv2.document.Table
import com.google.gson.Gson
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

/**
 * Alternative implementation using AWS SDK v1
 * Use this if you encounter compatibility issues with SDK v2
 */
class CreateDealHandlerV1 : RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    
    private val gson = Gson()
    private val dynamoDB = DynamoDB(AmazonDynamoDBClientBuilder.defaultClient())
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
            val table: Table = dynamoDB.getTable(tableName)
            val item = Item()
                .withPrimaryKey("dealId", dealId)
                .withString("name", request.name)
                .withString("description", request.description)
                .withNumber("categoryId", request.categoryId)
                .withString("categoryName", request.categoryName)
                .withNumber("serviceId", request.serviceId)
                .withString("serviceName", request.serviceName)
                .withNumber("latitude", request.latitude)
                .withNumber("longitude", request.longitude)
                .withBoolean("isUrgent", request.isUrgent)
                .withString("dateTime", request.dateTime)
                .withString("createdAt", System.currentTimeMillis().toString())
            
            table.putItem(item)
            
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




