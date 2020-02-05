package com.architecture.study.data.source.remote

import com.architecture.study.data.Result
import com.architecture.study.network.model.bithumb.BithumbResponse
import com.architecture.study.network.model.bithumb.BithumbSingleResponse
import io.reactivex.Single

interface BithumbRemoteDataSource {
    suspend fun getTickerList(): Result<BithumbResponse>
    suspend fun getTicker(orderCurrency: String, paymentCurrency: String): Result<BithumbSingleResponse>
}