package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.repository.ExchangeRepository
import com.architecture.study.ext.plusAssign

class ExchangeCompareViewModel(private val exchangeRepository: ExchangeRepository) :
    BaseViewModel() {

    private val _compareTickerList = MutableLiveData<List<CompareTicker>>()
    val compareTickerList: LiveData<List<CompareTicker>>
        get() = _compareTickerList

    fun getCompareTickerList(clickedTicker: CompareTicker) {
        _compareTickerList.value = listOf(clickedTicker)

        compositeDisposable += exchangeRepository.getCompareTickerList(
            clickedTicker.nowPrice.replace(",", "").toDouble(),
            clickedTicker.baseCurrency,
            clickedTicker.coinName,
            success = { compareTicker ->
                val addedTickerList = mutableListOf<CompareTicker>()
                compareTickerList.value?.let {
                    addedTickerList.addAll(it)
                }
                if (compareTicker.exchangeName != clickedTicker.exchangeName){
                    addedTickerList.add(compareTicker)
                }
                _compareTickerList.value = addedTickerList
            },
            failed = {

            }
        )
    }
}