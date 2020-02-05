package com.architecture.study.data.source.remote

import com.architecture.study.data.Result
import com.architecture.study.data.Result.Error
import com.architecture.study.data.Result.Success
import com.architecture.study.network.api.CoinoneApi
import com.architecture.study.network.model.coinone.CoinoneResponse

@Suppress("UNREACHABLE_CODE")
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
            } ?: Error(error("empty"))

        } catch (e: Exception) {
            Error(e)
        }
    }

}