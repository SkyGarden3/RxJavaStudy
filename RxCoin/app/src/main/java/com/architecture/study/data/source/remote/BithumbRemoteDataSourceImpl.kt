package com.architecture.study.data.source.remote

import com.architecture.study.network.api.BithumbApi
import com.architecture.study.network.model.bithumb.BithumbResponse
import com.architecture.study.network.model.bithumb.BithumbSingleResponse
import io.reactivex.Single

class BithumbRemoteDataSourceImpl(private val bithumbApi: BithumbApi): BithumbRemoteDataSource {

    override fun getTickerList(): Single<BithumbResponse> =
        bithumbApi.getAllTicker()

    override fun getTicker(orderCurrency: String, paymentCurrency: String): Single<BithumbSingleResponse> =
        bithumbApi.getTicker(orderCurrency, paymentCurrency)
}