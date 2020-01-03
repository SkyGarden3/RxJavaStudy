package com.architecture.study.network.api

import com.architecture.study.network.model.bithumb.BithumbResponse
import io.reactivex.Single
import retrofit2.http.GET

interface BithumbApi {
    @GET("/public/ticker/all")
    fun getAllTicker(): Single<BithumbResponse>
}