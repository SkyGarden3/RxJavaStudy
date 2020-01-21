package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker

interface ExchangeRepository {
    fun getCompareTickerList(
        clickedTicker: CompareTicker,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    )
}