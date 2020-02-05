package com.architecture.study.network.api

import com.architecture.study.network.model.bithumb.BithumbResponse
import com.architecture.study.network.model.bithumb.BithumbSingleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface BithumbApi {
    @GET("/public/ticker/all")
    suspend fun getAllTicker(): BithumbResponse

    @GET("/public/ticker/{order_currency}_{payment_currency}")
    suspend fun getTicker(
        @Path("order_currency") orderCurrency: String,
        @Path("payment_currency") paymentCurrency: String
    ): BithumbSingleResponse
}