package com.architecture.study.data.source.remote

import com.architecture.study.data.Result
import com.architecture.study.data.Result.Error
import com.architecture.study.data.Result.Success
import com.architecture.study.network.api.UpbitApi
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse

class UpbitRemoteDataSourceImpl(private val upbitApi: UpbitApi) :
    UpbitRemoteDataSource {

    override suspend fun getMarketList(): Result<List<UpbitMarketResponse>> {
        return try {
            Success(upbitApi.getMarketData())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun getTickerList(marketNames: String): Result<List<UpbitTickerResponse>> {
        return try {
            Success(upbitApi.getTickerData(marketNames))
        } catch (e: Exception) {
            Error(e)
        }
    }

}