package com.architecture.study.data.source.remote

import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.Single

interface UpbitRemoteDataSource {
    fun getMarketList(): Single<List<UpbitMarketResponse>>

    fun getTickerList(marketNames: String): Single<List<UpbitTickerResponse>>
}