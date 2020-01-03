package com.architecture.study.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.UpbitRepository
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import plusAssign

class TickerViewModel(
    private val upbitRepository: UpbitRepository,
    private val baseCurrency: String
) : BaseViewModel() {

    val tickerList = MutableLiveData<List<Ticker>>()
    val currencyMarketList = MutableLiveData<List<String>>()

    private val onClick: (ticker: Ticker) -> Unit = { ticker ->
        Log.d("TickerViewModel", "$ticker")
    }


    fun getTickerList() {
        currencyMarketList.value?.let {
            compositeDisposable += upbitRepository.getTickerList(it.joinToString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<UpbitTickerResponse>>() {
                    override fun onSuccess(tickerResponseList: List<UpbitTickerResponse>) {
                        val convertTickerList = mutableListOf<Ticker>()
                        tickerResponseList.map {
                            convertTickerList.add(it.toTicker(onClick))
                        }
                        if (convertTickerList.isEmpty()) {
                            exceptionMessage.value = "empty"
                        }
                        tickerList.value = convertTickerList
                    }

                    override fun onError(e: Throwable) {
                        exceptionMessage.value = e.message
                    }
                })
        } ?: run {
            compositeDisposable += upbitRepository.getMarketList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<UpbitMarketResponse>>() {
                    override fun onSuccess(marketResponseList: List<UpbitMarketResponse>) {
                        currencyMarketList.value = marketResponseList.asSequence()
                            .filter {
                                it.market.split("-")[0] ==
                                        baseCurrency
                            }
                            .map { it.market }
                            .toList()
                        getTickerList()
                    }

                    override fun onError(e: Throwable) {

                    }
                })
        }
    }
}