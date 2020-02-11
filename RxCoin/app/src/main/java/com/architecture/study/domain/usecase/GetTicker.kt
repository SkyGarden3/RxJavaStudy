package com.architecture.study.domain.usecase

import com.architecture.study.data.Result
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.repository.ExchangeRepository

class GetTicker(private val exchangeRepository: ExchangeRepository) {

    suspend operator fun invoke(
        clickedTicker: CompareTicker,
        callback: (result: Result<CompareTicker>) -> Unit
    ) {
        exchangeRepository.getCompareTickerList(
            clickedTicker = clickedTicker,
            callback = callback
        )
    }
}