package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.domain.usecase.GetTicker
import com.architecture.study.ext.plusAssign
import com.architecture.study.util.Event
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ExchangeCompareViewModel(private val getTicker: GetTicker) :
    BaseViewModel() {

    private val _compareTickerList = MutableLiveData<List<CompareTicker>>()
    val compareTickerList: LiveData<List<CompareTicker>>
        get() = _compareTickerList

    private lateinit var lastClickedTicker: CompareTicker

    private val success: (tickers: CompareTicker) -> Unit = { compareTicker ->
        val addedTickerList = mutableListOf<CompareTicker>()
        compareTickerList.value?.let {
            addedTickerList.addAll(it)
        }
        addedTickerList.add(compareTicker)
        _compareTickerList.value = addedTickerList

        if (::lastClickedTicker.isInitialized) {
            setComparePriceTickerList(lastClickedTicker.exchangeName)
        }
    }
    private val failed: (errorCode: String) -> Unit = { message ->
        _exceptionMessage.value = Event(message)
    }

    fun getCompareTickerList(clickedTicker: CompareTicker) {

        lastClickedTicker = clickedTicker

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _compareTickerList.value = listOf()
                getTicker(clickedTicker, success, failed)
            }, {
                _exceptionMessage.value = Event("${it.message}")
            })
    }

    private fun setComparePriceTickerList(baseExchange: String) {
        compareTickerList.value?.let { compareTickerList ->
            _compareTickerList.value =
                compareTickerList.sortedBy { it.exchangeName != baseExchange }
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
