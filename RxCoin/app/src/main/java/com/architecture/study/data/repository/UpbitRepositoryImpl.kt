package com.architecture.study.data.repository

import com.architecture.study.data.source.remote.UpbitRemoteDataSource
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.Single

class UpbitRepositoryImpl (private val upbitRemoteDataSource: UpbitRemoteDataSource) : UpbitRepository {
    override fun getMarketList(): Single<List<UpbitMarketResponse>> =
        upbitRemoteDataSource.getMarketList()

    override fun getTickerList(marketNames: String): Single<List<UpbitTickerResponse>> =
        upbitRemoteDataSource.getTickerList(marketNames)
}