package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.architecture.study.base.BaseViewModel
import com.architecture.study.util.Result
import com.architecture.study.util.Result.Error
import com.architecture.study.util.Result.Success
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.usecase.GetTicker
import com.architecture.study.ext.plusAssign
import com.architecture.study.util.Event
import com.architecture.study.view.coin.model.CompareTickerItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class ExchangeCompareViewModel(private val getTicker: GetTicker) :
    BaseViewModel() {

    private val _compareTickerList = MutableLiveData<List<CompareTickerItem>>()
    val compareTickerList: LiveData<List<CompareTickerItem>>
        get() = _compareTickerList

    private lateinit var lastClickedTicker: CompareTickerItem

    private val callback: (tickers: Result<CompareTicker>) -> Unit = { result ->

        (result as? Success)?.data?.let { compareTicker ->
            val addedTickerList = mutableListOf<CompareTickerItem>()
            compareTickerList.value?.let {
                addedTickerList.addAll(it)
            }
            addedTickerList.add(CompareTickerItem.of(compareTicker))
            _compareTickerList.value = addedTickerList

            if (::lastClickedTicker.isInitialized) {
                setComparePriceTickerList(lastClickedTicker.exchangeName)
            }
        } ?: run {
            _exceptionMessage.value =
                Event((result as Error).exception.message.orEmpty())
        }
    }

    fun getCompareTickerList(clickedTicker: CompareTickerItem) {

        lastClickedTicker = clickedTicker

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _compareTickerList.value = listOf()
                viewModelScope.launch {
                    getTicker(clickedTicker.baseCurrency, clickedTicker.coinName, callback)
                }
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
