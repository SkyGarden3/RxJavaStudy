package com.architecture.study.network.api

import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UpbitApi {
    @GET("/v1/market/all")
    fun getMarketData(): Single<List<UpbitMarketResponse>>

    @GET("/v1/ticker?")
    fun getTickerData(
        @Query("markets") markets: String
    ): Single<List<UpbitTickerResponse>>
}