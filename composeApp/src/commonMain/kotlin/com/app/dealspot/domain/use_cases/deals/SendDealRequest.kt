package com.app.dealspot.domain.use_cases.deals

import com.app.dealspot.data.DealRepositoryImpl
import com.app.dealspot.domain.model.DealRequest

class SendDealRequest(
    private val dealRepositoryImpl: DealRepositoryImpl
) {
    suspend operator fun invoke(dealId: String, dealType: Int, requestType: Int, userSub: String) {
        dealRepositoryImpl.requestToDeal(DealRequest(dealId = dealId, dealType = dealType, requestType = requestType, userSub = userSub))
    }
}