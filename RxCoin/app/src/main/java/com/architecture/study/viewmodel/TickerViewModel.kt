package com.architecture.study.viewmodel

import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architecture.study.App
import com.architecture.study.base.BaseViewModel
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.repository.TickerRepository
import com.architecture.study.util.PrefUtil
import com.architecture.study.view.coin.CoinListActivity
import com.architecture.study.view.coin.ExchangeCompareActivity
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named

class TickerViewModel(private val baseCurrency: String) : BaseViewModel() {


    private val context = App.instance.context()

    private val _tickerList = MutableLiveData<List<Ticker>>()
    val tickerList: LiveData<List<Ticker>>
        get() = _tickerList

    private val _clickedTicker = MutableLiveData<CompareTicker>()
    val clickedTicker: LiveData<CompareTicker>
        get() = _clickedTicker

    private lateinit var tickerRepository: TickerRepository
    private var currentExchange = ""

    private val onClick: (ticker: Ticker) -> Unit = { ticker ->
        _clickedTicker.value =
            ticker.toCompareTicker(basePrice = ticker.nowPrice.replace(",", "").toDouble()).apply {
                baseCurrency = this@TickerViewModel.baseCurrency
                exchangeName = currentExchange
            }

        val intent = Intent(context, ExchangeCompareActivity::class.java).apply {
            putExtra(ExchangeCompareActivity.CLICKED_TICKER, _clickedTicker.value!!)
        }
        context.startActivity(intent)
    }

    fun start(currentExchange: String) {
        this.currentExchange = currentExchange
        initTickerRepository(currentExchange)
    }

    private fun initTickerRepository(currentExchange: String) {
        tickerRepository = App.instance.get(named(currentExchange))
    }

    fun getTickerList() {
        tickerRepository.getAllTicker(baseCurrency = baseCurrency,
            success = {
                _tickerList.value = it
                tickerRepository.finish()
            }, failed = {
                tickerRepository.finish()
            }, onClick = onClick
        )
    }
}