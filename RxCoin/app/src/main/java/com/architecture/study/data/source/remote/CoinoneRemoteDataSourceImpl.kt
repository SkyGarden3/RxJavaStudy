package com.architecture.study.data.source.remote

import com.architecture.study.network.api.CoinoneApi
import com.architecture.study.network.model.coinone.CoinoneResponse
import io.reactivex.Single

class CoinoneRemoteDataSourceImpl(private val coinoneApi: CoinoneApi) : CoinoneRemoteDataSource {

    override fun getTickerList(): Single<Map<String, Any>> =
        coinoneApi.getAllTicker()


    override fun getTicker(currency: String): Single<CoinoneResponse> =
        coinoneApi.getTicker(currency)
}