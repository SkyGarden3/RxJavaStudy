package com.architecture.study.data.source.remote

import com.architecture.study.util.Result
import com.architecture.study.util.Result.Success
import com.architecture.study.util.Result.Error
import com.architecture.study.network.api.BithumbApi
import com.architecture.study.network.model.bithumb.BithumbResponse
import com.architecture.study.network.model.bithumb.BithumbSingleResponse

class BithumbRemoteDataSourceImpl(private val bithumbApi: BithumbApi): BithumbRemoteDataSource {

    override suspend fun getTickerList(): Result<BithumbResponse> {
        return try {
            Success(bithumbApi.getAllTicker())
        } catch (e: Exception){
            Error(e)
        }

    }


    override suspend fun getTicker(orderCurrency: String, paymentCurrency: String): Result<BithumbSingleResponse> {
        return try {
            Success(bithumbApi.getTicker(orderCurrency, paymentCurrency))
        } catch (e: Exception){
            Error(e)
        }
    }

}