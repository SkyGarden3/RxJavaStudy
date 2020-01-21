package com.architecture.study.domain.usecase

import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.TickerRepository

class GetAllTicker(private val tickerRepository: TickerRepository) :
    UseCase<GetAllTicker.RequestValues, GetAllTicker.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        requestValues?.let {
            tickerRepository.getAllTicker(
                baseCurrency = it.baseCurrency,
                success = { tickerList ->
                    useCaseCallback?.onSuccess(ResponseValue(tickerList))
                    tickerRepository.finish()
                },
                failed = { message ->
                    useCaseCallback?.onError(message)
                    tickerRepository.finish()
                },
                onClick = requestValues.onClick
            )
        }
    }

    class RequestValues(val baseCurrency: String, val onClick: (ticker: Ticker) -> Unit) :
        UseCase.RequestValues

    class ResponseValue(val tickerList: List<Ticker>) : UseCase.ResponseValue
}