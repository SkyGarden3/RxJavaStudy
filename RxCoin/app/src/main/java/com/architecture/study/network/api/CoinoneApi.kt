package com.architecture.study.network.api

import com.architecture.study.network.model.coinone.CoinoneResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinoneApi {

    @GET("/ticker?currency=all")
    fun getAllTicker(): Single<Map<String, Any>>

    @GET("/ticker")
    fun getTicker(@Query("currency") currency: String): Single<CoinoneResponse>
}