package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.domain.usecase.GetTicker
import com.architecture.study.domain.usecase.UseCase
import com.architecture.study.ext.plusAssign
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

    init {
        getTicker.useCaseCallback = object : UseCase.UseCaseCallback<GetTicker.ResponseValue> {
            override fun onSuccess(response: GetTicker.ResponseValue) {

                val addedTickerList = mutableListOf<CompareTicker>()
                compareTickerList.value?.let {
                    addedTickerList.addAll(it)
                }
                addedTickerList.add(response.compareTicker)
                _compareTickerList.value = addedTickerList

                if (::lastClickedTicker.isInitialized) {
                    setComparePriceTickerList(lastClickedTicker.exchangeName)
                }
            }

            override fun onError() {

            }
        }
    }

    fun getCompareTickerList(clickedTicker: CompareTicker) {

        lastClickedTicker = clickedTicker

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _compareTickerList.value = listOf()
                getTicker.requestValues = GetTicker.RequestValues(clickedTicker)
                getTicker.run()
            }, {

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
