package com.architecture.study.data.repository

import com.architecture.study.util.Result
import com.architecture.study.util.enums.getBaseCurrencies
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.repository.ExchangeRepository
import com.architecture.study.domain.repository.TickerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ExchangeRepositoryImpl(private val tickerRepositoryMap: Map<String, TickerRepository>) :
    ExchangeRepository {

    override suspend fun getCompareTickerList(
        baseCurrency: String,
        coinName: String,
        callback: (result: Result<CompareTicker>) -> Unit
    ) {

        tickerRepositoryMap.forEach { (exchange, repository) ->
            withContext(Dispatchers.IO) {
                getBaseCurrencies(exchange)?.contains(baseCurrency)
                    ?.let { isContains ->
                        if (isContains) {
                            val compareTickerResult = repository.getTicker(
                                baseCurrency = baseCurrency,
                                coinName = coinName
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