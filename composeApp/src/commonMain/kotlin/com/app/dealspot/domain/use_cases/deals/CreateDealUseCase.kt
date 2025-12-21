package com.app.dealspot.domain.use_cases.deals

import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.data.model.CreateDealRequest
import com.app.dealspot.data.model.CreateDealResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class CreateDealUseCase(
    private val dealRepositoryImpl: DealRepositoryImpl
) {

    suspend fun createDeal(request: CreateDealRequest): CreateDealResponse {
        return withContext(Dispatchers.IO) {
            dealRepositoryImpl.createDeal(request = request)
        }
    }

}