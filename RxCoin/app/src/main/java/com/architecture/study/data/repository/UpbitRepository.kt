package com.architecture.study.data.repository

import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.Single

interface UpbitRepository {
    fun getMarketList(): Single<List<UpbitMarketResponse>>

    fun getTickerList(marketNames: String): Single<List<UpbitTickerResponse>>
}