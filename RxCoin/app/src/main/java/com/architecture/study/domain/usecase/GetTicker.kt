package com.architecture.study.domain.usecase

import com.architecture.study.util.Result
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.repository.ExchangeRepository

class GetTicker(private val exchangeRepository: ExchangeRepository) {

    suspend operator fun invoke(
        baseCurrency: String,
        coinName: String,
        callback: (result: Result<CompareTicker>) -> Unit
    ) {
        exchangeRepository.getCompareTickerList(
            baseCurrency = baseCurrency,
            coinName = coinName,
            callback = callback
        )
    }
}