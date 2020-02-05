package com.architecture.study.data.source.remote

import com.architecture.study.data.Result
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse

interface UpbitRemoteDataSource {
    suspend fun getMarketList(): Result<List<UpbitMarketResponse>>

    suspend fun getTickerList(marketNames: String): Result<List<UpbitTickerResponse>>
}