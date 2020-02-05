package com.architecture.study.data.repository

import com.architecture.study.data.Result.Error
import com.architecture.study.data.Result.Success
import com.architecture.study.data.enums.getBaseCurrencies
import com.architecture.study.data.model.CompareTicker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExchangeRepositoryImpl(private val tickerRepositoryMap: Map<String, TickerRepository>) :
    ExchangeRepository {

    override fun getCompareTickerList(
        clickedTicker: CompareTicker,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        tickerRepositoryMap.forEach { (exchange, repository) ->
            CoroutineScope(Dispatchers.IO).launch {
                getBaseCurrencies(exchange)?.contains(clickedTicker.baseCurrency)
                    ?.let { isContains ->
                        if (isContains) {
                            val compareTickerResult = repository.getTicker(
                                baseCurrency = clickedTicker.baseCurrency,
                                coinName = clickedTicker.coinName
                            )
                            CoroutineScope(Dispatchers.Main).launch {
                                (compareTickerResult as? Success)?.data?.let {
                                    success.invoke(it)
                                } ?: run {
                                    failed.invoke((compareTickerResult as Error).exception.message.orEmpty())
                                }
                            }
                        }
                    }
            }
        }
    }
}