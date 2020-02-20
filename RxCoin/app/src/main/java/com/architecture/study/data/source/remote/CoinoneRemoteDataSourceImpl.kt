package com.architecture.study.data.source.remote

import com.architecture.study.util.Result
import com.architecture.study.util.Result.Error
import com.architecture.study.util.Result.Success
import com.architecture.study.network.api.CoinoneApi
import com.architecture.study.network.model.coinone.CoinoneResponse

class CoinoneRemoteDataSourceImpl(private val coinoneApi: CoinoneApi) : CoinoneRemoteDataSource {

    override suspend fun getTickerList(): Result<Map<String, Any>> {
        return try {
            Success(coinoneApi.getAllTicker())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTicker(currency: String): Result<CoinoneResponse> {
        return try {
            val coinoneResponse = coinoneApi.getTicker(currency)
            coinoneResponse.currency?.let {
                Success(coinoneResponse)
            } ?: Error(Exception("not found coin : Coinone"))
        } catch (e: Exception) {
            Error(e)
        }
    }

}