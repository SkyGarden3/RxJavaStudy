package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker
import io.reactivex.disposables.CompositeDisposable

class ExchangeRepository(private val tickerRepositoryMap: Map<String, TickerRepository>) {
    fun getCompareTickerList(
        basePrice: Double,
        baseCurrency: String,
        coinName: String,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ): CompositeDisposable {
        val compositeDisposable = CompositeDisposable()
        tickerRepositoryMap.forEach { (exchange, repository) ->
            repository.getTicker(
                basePrice = basePrice,
                baseCurrency = baseCurrency,
                coinName = coinName,
                success = {
                    success(it)
                },
                failed = {
                    failed(it)
                }
            )
        }
        return compositeDisposable
    }
}