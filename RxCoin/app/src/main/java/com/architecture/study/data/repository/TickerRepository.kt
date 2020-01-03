package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker

interface TickerRepository {
    fun getAllTicker(
        baseCurrency: String? = "",
        success: (tickers: List<Ticker>) -> Unit,
        failed: (errorCode: String) -> Unit,
        onClick: (ticker: Ticker) -> Unit
    )

    fun getTicker(
        basePrice: Double,
        baseCurrency: String,
        coinName: String,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    )

    fun finish()
}