package com.app.dealspot.data

import com.app.dealspot.data.model.CreateDealRequest
import com.app.dealspot.data.model.CreateDealResponse
import com.dealspot.network.AWSConfig
import com.dealspot.network.apiGetawayClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class DealRepositoryImpl() {
    private val client = apiGetawayClient()
    
    suspend fun createDeal(request: CreateDealRequest): CreateDealResponse {
        return try {
            println("DealRepositoryImpl. createDeal. Request: $request")
            val response = client.post("${AWSConfig.API_GATEWAY_URL_DEV}/createDeal") {
                setBody(request)
            }
            val result: CreateDealResponse = response.body()
            println("DealRepositoryImpl. createDeal. Response: $result")
            if (result.success) {
//                Result.success(result)
                result
            } else {
//                Result.failure(Exception(result.message))
                result
            }
        } catch (e: Exception) {
            println("DealRepositoryImpl. createDeal. Error: ${e.message}")

            e.printStackTrace()
//            Result.failure(e)
            CreateDealResponse(
                success = false,
                message = "Error happen. Please try again"
            )
        }
    }
}
