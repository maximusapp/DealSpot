package com.app.dealspot.data

import com.app.dealspot.domain.model.CreateDealRequest
import com.app.dealspot.domain.model.CreateDealResponse
import com.app.dealspot.domain.model.DealRequest
import com.app.dealspot.domain.model.GetDealsRequest
import com.app.dealspot.domain.model.GetDealsResponse
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
    
    suspend fun getDeals(request: GetDealsRequest): GetDealsResponse {
        return try {
            println("DealRepositoryImpl. getDeals. Request: $request")
            val response = client.post("${AWSConfig.API_GATEWAY_URL_DEV}/getDeals") {
                setBody(request)
            }
            val result: GetDealsResponse = response.body()
            println("DealRepositoryImpl. getDeals. Response: ${result.count} deals found")
            result
        } catch (e: Exception) {
            println("DealRepositoryImpl. getDeals. Error: ${e.message}")
            e.printStackTrace()
            GetDealsResponse(items = emptyList(), count = 0)
        }
    }

    suspend fun getDeal(dealId: String) {

    }

    suspend fun requestToDeal(dealRequest: DealRequest) {

    }
}
