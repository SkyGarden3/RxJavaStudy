package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.BithumbRemoteDataSource
import com.architecture.study.ext.plusAssign
import com.architecture.study.network.model.bithumb.BithumbTickerResponse
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class BithumbRepository(private val bithumbRemoteDataSource: BithumbRemoteDataSource) :
    TickerRepository {

    private val compositeDisposable = CompositeDisposable()
    private var subscribeCount = 0

    override fun getAllTicker(
        baseCurrency: String?,
        success: (tickers: List<Ticker>) -> Unit,
        failed: (errorCode: String) -> Unit,
        onClick: (ticker: Ticker) -> Unit
    ) {
        compositeDisposable += bithumbRemoteDataSource.getTickerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                val gson = Gson()
                response.tickerResponse
                    .filter { it.key != "date" }
                    .map { (currency, tickerResponse) ->
                        gson.fromJson(tickerResponse.toString(), BithumbTickerResponse::class.java)
                            .toTicker(onClick, currency)
                    }
            }
            .subscribeWith(object : DisposableSingleObserver<List<Ticker>>() {
                override fun onSuccess(tickerList: List<Ticker>) {
                    tickerList.let(success)
                }

                override fun onError(e: Throwable) {
                    e.message?.let(failed)
                }
            })
        ++subscribeCount
    }

    override fun getTicker(
        baseCurrency: String,
        coinName: String,
        success: (ticker: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        compositeDisposable += bithumbRemoteDataSource
            .getTicker(orderCurrency = coinName, paymentCurrency = baseCurrency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { response ->
                response.tickerResponse.toCompareTicker(coinName)
            }
            .subscribeWith(object : DisposableSingleObserver<CompareTicker>() {
                override fun onSuccess(compareTicker: CompareTicker) {
                    compareTicker.let(success)
                }

                override fun onError(e: Throwable) {
                    e.message?.let(failed)
                }
            })
        ++subscribeCount
    }

    override fun finish() {
        --subscribeCount
        if (subscribeCount == 0) {
            compositeDisposable.clear()
        }
    }
}