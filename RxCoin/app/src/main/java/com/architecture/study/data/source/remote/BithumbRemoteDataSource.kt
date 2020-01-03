package com.architecture.study.data.source.remote

import com.architecture.study.network.model.bithumb.BithumbResponse
import io.reactivex.Single

interface BithumbRemoteDataSource {
    fun getTickerList(): Single<BithumbResponse>
}