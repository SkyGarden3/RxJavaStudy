package com.architecture.study.domain.usecase

import com.architecture.study.data.Result
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.TickerRepository

class GetAllTicker(private val tickerRepository: TickerRepository) {

    suspend operator fun invoke(baseCurrency: String, onClick: (ticker: Ticker) -> Unit): Result<List<Ticker>> {
        return tickerRepository.getAllTicker(baseCurrency = baseCurrency, onClick = onClick)
    }
}
