package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.architecture.study.App
import com.architecture.study.base.BaseViewModel
import com.architecture.study.util.Result.Error
import com.architecture.study.util.Result.Success
import com.architecture.study.domain.usecase.GetAllTicker
import com.architecture.study.ext.plusAssign
import com.architecture.study.util.Event
import com.architecture.study.view.coin.model.CompareTickerItem
import com.architecture.study.view.coin.model.TickerItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class TickerViewModel(private val baseCurrency: String) : BaseViewModel() {


    private val context = App.instance.context()

    private val _tickerList = MutableLiveData<List<TickerItem>>()
    val tickerList: LiveData<List<TickerItem>>
        get() = _tickerList

    private val _clickedTicker = MutableLiveData<Event<CompareTickerItem>>()
    val clickedTicker: LiveData<Event<CompareTickerItem>>
        get() = _clickedTicker


    private lateinit var getAllTicker: GetAllTicker

    private var currentExchange = ""

    private val onClick: (ticker: TickerItem) -> Unit = { tickerItem ->
        _clickedTicker.value =
            Event(
                CompareTickerItem.of(tickerItem, baseCurrency, currentExchange)
            )
    }

    fun start(currentExchange: String) {

        this.currentExchange = currentExchange
        initUseCase(currentExchange)
    }

    private fun initUseCase(currentExchange: String) {
        getAllTicker = App.instance.get { parametersOf(currentExchange) }
    }

    fun getTickerList() {

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .startWith(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

                viewModelScope.launch {

                    val tickerResult = getAllTicker(baseCurrency)

                    (tickerResult as? Success)?.data?.let { tickerList ->
                        val sortedList = tickerList.sortedByDescending { it.nowPrice.toDouble() }
                        _tickerList.value = sortedList.map { ticker ->
                            TickerItem.of(ticker, onClick)
                        }
                    } ?: run {
                        if (tickerResult is Error) {
                            _exceptionMessage.value =
                                Event(tickerResult.exception.message.orEmpty())
                        }
                    }
                }

            }, {
                _exceptionMessage.value = Event("${it.message}")
            })

    }
}