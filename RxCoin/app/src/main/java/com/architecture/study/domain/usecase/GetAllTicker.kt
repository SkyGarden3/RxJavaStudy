package com.architecture.study.domain.usecase

import com.architecture.study.util.Result
import com.architecture.study.domain.model.Ticker
import com.architecture.study.domain.repository.TickerRepository

class GetAllTicker(private val tickerRepository: TickerRepository) {

    suspend operator fun invoke(baseCurrency: String): Result<List<Ticker>> {
        return tickerRepository.getAllTicker(baseCurrency = baseCurrency)
    }
}
