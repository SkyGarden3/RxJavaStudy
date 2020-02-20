package com.architecture.study.domain.repository

import com.architecture.study.util.Result
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.model.Ticker

interface TickerRepository {
    suspend fun getAllTicker(
        baseCurrency: String? = ""
    ): Result<List<Ticker>>

    suspend fun getTicker(
        baseCurrency: String,
        coinName: String
    ): Result<CompareTicker>
}