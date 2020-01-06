package com.architecture.study.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.repository.ExchangeRepository
import com.architecture.study.ext.plusAssign
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ExchangeCompareViewModel(private val exchangeRepository: ExchangeRepository) :
    BaseViewModel() {

    private val _compareTickerList = MutableLiveData<List<CompareTicker>>()
    val compareTickerList: LiveData<List<CompareTicker>>
        get() = _compareTickerList

    fun getCompareTickerList(clickedTicker: CompareTicker) {
        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _compareTickerList.value = listOf()
                exchangeRepository.getCompareTickerList(
                    clickedTicker,
                    success = { compareTicker ->
                        val addedTickerList = mutableListOf<CompareTicker>()
                        compareTickerList.value?.let {
                            addedTickerList.addAll(it)
                        }
                        addedTickerList.add(compareTicker)
                        _compareTickerList.value = addedTickerList
                        setComparePriceTickerList(clickedTicker.exchangeName)
                    },
                    failed = {

                    }
                )
            }, {

            })
    }

    private fun setComparePriceTickerList(baseExchange: String) {
        compareTickerList.value?.let { compareTickerList ->
            _compareTickerList.value = compareTickerList.sortedBy { it.exchangeName != baseExchange }
            val basePrice = compareTickerList.find { it.exchangeName == baseExchange }
                ?.nowPrice
                ?.replace(",", "")
                ?.toDouble()
            compareTickerList.forEach { compareTicker ->
                basePrice?.let {
                        compareTicker.comparePrice =
                            compareTicker.nowPrice.replace(",", "").toDouble() - it
                    }
            }
        }
    }
}