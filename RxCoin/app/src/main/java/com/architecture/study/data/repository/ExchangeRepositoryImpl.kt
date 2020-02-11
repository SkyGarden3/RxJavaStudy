package com.architecture.study.data.repository

import com.architecture.study.data.Result
import com.architecture.study.data.enums.getBaseCurrencies
import com.architecture.study.data.model.CompareTicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRepositoryImpl(private val tickerRepositoryMap: Map<String, TickerRepository>) :
    ExchangeRepository {

    override suspend fun getCompareTickerList(
        clickedTicker: CompareTicker,
        callback: (result: Result<CompareTicker>) -> Unit
    ) {

        tickerRepositoryMap.forEach { (exchange, repository) ->
            withContext(Dispatchers.IO) {
                getBaseCurrencies(exchange)?.contains(clickedTicker.baseCurrency)
                    ?.let { isContains ->
                        if (isContains) {
                            val compareTickerResult = repository.getTicker(
                                baseCurrency = clickedTicker.baseCurrency,
                                coinName = clickedTicker.coinName
                            )
                            withContext(Dispatchers.Main) {
                                callback(compareTickerResult)
                            }
                        }
                    }
            }
        }
    }

}