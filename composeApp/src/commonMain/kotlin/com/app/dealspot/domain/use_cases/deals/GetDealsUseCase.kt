package com.app.dealspot.domain.use_cases.deals

import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.data.model.GetDealsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class GetDealsUseCase(
    private val dealRepository: DealRepositoryImpl
) {
    suspend operator fun invoke(type: Int): GetDealsResponse {
        return withContext(Dispatchers.IO) {
            dealRepository.getDeals(com.app.dealspot.data.model.GetDealsRequest(type = type))
        }
    }
}

