package com.architecture.study.domain.usecase

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.repository.ExchangeRepository
import com.architecture.study.data.repository.ExchangeRepositoryImpl

class GetTicker(private val exchangeRepository: ExchangeRepository) :
    UseCase<GetTicker.RequestValues, GetTicker.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues?) {
        requestValues?.let {
            exchangeRepository.getCompareTickerList(
                it.clickedTicker,
                success = { compareTicker ->
                    useCaseCallback?.onSuccess(ResponseValue(compareTicker))
                },
                failed = {

                }
            )
        }
    }

    class RequestValues(val clickedTicker: CompareTicker) :
        UseCase.RequestValues

    class ResponseValue(val compareTicker: CompareTicker) : UseCase.ResponseValue
}