package com.architecture.study.domain.usecase

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.repository.ExchangeRepository

class GetTicker(private val exchangeRepository: ExchangeRepository) {

    operator fun invoke(
        clickedTicker: CompareTicker,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        exchangeRepository.getCompareTickerList(
            clickedTicker = clickedTicker,
            success = success,
            failed = failed
        )
    }
}