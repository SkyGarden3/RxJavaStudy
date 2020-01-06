package com.architecture.study.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.App
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.TickerRepository
import com.architecture.study.ext.plusAssign
import com.architecture.study.util.Event
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import java.util.concurrent.TimeUnit

class TickerViewModel(private val baseCurrency: String) : BaseViewModel() {


    private val context = App.instance.context()

    private val _tickerList = MutableLiveData<List<Ticker>>()
    val tickerList: LiveData<List<Ticker>>
        get() = _tickerList

    private val _clickedTicker = MutableLiveData<Event<CompareTicker>>()
    val clickedTicker: LiveData<Event<CompareTicker>>
        get() = _clickedTicker

    private lateinit var tickerRepository: TickerRepository
    private var currentExchange = ""

    private val onClick: (ticker: Ticker) -> Unit = { ticker ->
        _clickedTicker.value =
            Event(
                ticker.toCompareTicker().apply {
                    baseCurrency = this@TickerViewModel.baseCurrency
                    exchangeName = currentExchange
                }
            )
    }

    init {
        Log.d("TickerViewModel", "init")
    }

    fun start(currentExchange: String) {
        this.currentExchange = currentExchange
        initTickerRepository(currentExchange)
    }

    private fun initTickerRepository(currentExchange: String) {
        tickerRepository = App.instance.get(named(currentExchange))
    }

    fun getTickerList() {

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .startWith(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                tickerRepository.getAllTicker(baseCurrency = baseCurrency,
                    success = {
                        _tickerList.value = it
                        tickerRepository.finish()
                    }, failed = {
                        tickerRepository.finish()
                    }, onClick = onClick
                )
            }, {

            })

    }
}