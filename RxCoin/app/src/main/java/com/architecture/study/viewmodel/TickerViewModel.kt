package com.architecture.study.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.App
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.domain.usecase.GetAllTicker
import com.architecture.study.domain.usecase.UseCase
import com.architecture.study.ext.plusAssign
import com.architecture.study.util.Event
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.get
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit

class TickerViewModel(private val baseCurrency: String) : BaseViewModel() {


    private val context = App.instance.context()

    private val _tickerList = MutableLiveData<List<Ticker>>()
    val tickerList: LiveData<List<Ticker>>
        get() = _tickerList

    private val _clickedTicker = MutableLiveData<Event<CompareTicker>>()
    val clickedTicker: LiveData<Event<CompareTicker>>
        get() = _clickedTicker


    private lateinit var getAllTicker: GetAllTicker

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

    fun start(currentExchange: String) {
        this.currentExchange = currentExchange
        initUseCase(currentExchange)
    }

    private fun initUseCase(currentExchange: String) {
        getAllTicker = App.instance
            .get<GetAllTicker> { parametersOf(currentExchange) }
            .apply {
                useCaseCallback = object : UseCase.UseCaseCallback<GetAllTicker.ResponseValue> {
                    override fun onSuccess(response: GetAllTicker.ResponseValue) {
                        val sortedList = response.tickerList.sortedByDescending { it.nowPrice.toDouble() }
                        _tickerList.value = sortedList
                    }

                    override fun onError(message: String) {
                        _exceptionMessage.value = Event(message)
                    }
                }
            }
    }

    fun getTickerList() {

        compositeDisposable += Observable.interval(0, 5000L, TimeUnit.MILLISECONDS)
            .startWith(0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getAllTicker.requestValues = GetAllTicker.RequestValues(baseCurrency, onClick)
                getAllTicker.run()
            }, {
                _exceptionMessage.value = Event("${it.message}")
            })

    }
}