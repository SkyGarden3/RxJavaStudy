package com.architecture.study.data.source.remote

import com.architecture.study.network.model.coinone.CoinoneResponse
import io.reactivex.Single

interface CoinoneRemoteDataSource {
    fun getTickerList(): Single<Map<String, Any>>
    fun getTicker(currency: String): Single<CoinoneResponse>
}