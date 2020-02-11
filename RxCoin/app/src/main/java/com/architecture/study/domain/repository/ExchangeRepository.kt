package com.architecture.study.domain.repository

import com.architecture.study.util.Result
import com.architecture.study.domain.model.CompareTicker

interface ExchangeRepository {
    suspend fun getCompareTickerList(
        baseCurrency: String,
        coinName: String,
        callback: (result: Result<CompareTicker>) -> Unit
    )
}