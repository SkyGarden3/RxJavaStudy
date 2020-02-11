package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.Result

interface ExchangeRepository {
    suspend fun getCompareTickerList(
        clickedTicker: CompareTicker,
        callback: (result: Result<CompareTicker>) -> Unit
    )
}