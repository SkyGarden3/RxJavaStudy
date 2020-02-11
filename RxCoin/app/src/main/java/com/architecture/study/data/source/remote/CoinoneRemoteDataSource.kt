package com.architecture.study.data.source.remote

import com.architecture.study.util.Result
import com.architecture.study.network.model.coinone.CoinoneResponse

interface CoinoneRemoteDataSource {
    suspend fun getTickerList(): Result<Map<String, Any>>
    suspend fun getTicker(currency: String): Result<CoinoneResponse>
}