package com.architecture.study.data.repository

import com.architecture.study.data.Result
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker

interface TickerRepository {
    suspend fun getAllTicker(
        baseCurrency: String? = "",
        onClick: (ticker: Ticker) -> Unit
    ): Result<List<Ticker>>

    suspend fun getTicker(
        baseCurrency: String,
        coinName: String
    ): Result<CompareTicker>
}