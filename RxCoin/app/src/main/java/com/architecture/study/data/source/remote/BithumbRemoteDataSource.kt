package com.architecture.study.data.source.remote

import com.architecture.study.network.model.bithumb.BithumbResponse
import com.architecture.study.network.model.bithumb.BithumbSingleResponse
import io.reactivex.Single

interface BithumbRemoteDataSource {
    fun getTickerList(): Single<BithumbResponse>
    fun getTicker(orderCurrency: String, paymentCurrency: String): Single<BithumbSingleResponse>
}