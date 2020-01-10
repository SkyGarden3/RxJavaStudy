package com.architecture.study.domain.usecase

import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.TickerRepository

class GetAllTicker(private val tickerRepository: TickerRepository) :
    UseCase<GetAllTicker.RequestValues, GetAllTicker.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        requestValues?.let {
            tickerRepository.getAllTicker(
                it.baseCurrency, { tickerList ->
                    useCaseCallback?.onSuccess(ResponseValue(tickerList))
                    tickerRepository.finish()
                }, {
                    tickerRepository.finish()
                },
                requestValues.onClick
            )
        }
    }

    class RequestValues(val baseCurrency: String, val onClick: (ticker: Ticker) -> Unit) :
        UseCase.RequestValues

    class ResponseValue(val tickerList: List<Ticker>) : UseCase.ResponseValue
}