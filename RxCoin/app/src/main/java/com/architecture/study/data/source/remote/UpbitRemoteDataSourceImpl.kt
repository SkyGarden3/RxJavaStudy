package com.architecture.study.data.source.remote

import com.architecture.study.network.api.UpbitApi
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpbitRemoteDataSourceImpl (private val upbitApi: UpbitApi) :
    UpbitRemoteDataSource {
    override fun getMarketList(): Single<List<UpbitMarketResponse>> =
        upbitApi.getMarketData()

    override fun getTickerList(marketNames: String): Single<List<UpbitTickerResponse>> =
        upbitApi.getTickerData(marketNames)
}