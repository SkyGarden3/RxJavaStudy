package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker

class ExchangeRepository(private val tickerRepositoryMap: Map<String, TickerRepository>) {

    fun getCompareTickerList(
        clickedTicker: CompareTicker,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        tickerRepositoryMap.forEach { (exchange, repository) ->
            repository.getTicker(
                baseCurrency = clickedTicker.baseCurrency,
                coinName = clickedTicker.coinName,
                success = {
                    success(it)
                },
                failed = {
                    failed(it)
                }
            )
        }
    }
}