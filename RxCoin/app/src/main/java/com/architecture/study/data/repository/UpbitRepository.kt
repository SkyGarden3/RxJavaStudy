package com.architecture.study.data.repository

import android.util.Log
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.UpbitRemoteDataSource
import com.architecture.study.ext.plusAssign
import com.architecture.study.network.model.upbit.UpbitMarketResponse
import com.architecture.study.network.model.upbit.UpbitTickerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UpbitRepository(private val upbitRemoteDataSource: UpbitRemoteDataSource) : TickerRepository {

    private val compositeDisposable = CompositeDisposable()
    private var subscribeCount = 0

    override fun getAllTicker(
        baseCurrency: String?,
        success: (tickers: List<Ticker>) -> Unit,
        failed: (errorCode: String) -> Unit,
        onClick: (ticker: Ticker) -> Unit
    ) {

        compositeDisposable += upbitRemoteDataSource.getMarketList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { markerResponseList ->
                markerResponseList.asSequence()
                    .map { it.market }
                    .filter {
                        it.split("-")[0] ==
                                baseCurrency
                    }
                    .toList()
            }
            .subscribeWith(object : DisposableSingleObserver<List<String>>() {
                override fun onSuccess(markets: List<String>) {
                    compositeDisposable += upbitRemoteDataSource.getTickerList(markets.joinToString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(object :
                            DisposableSingleObserver<List<UpbitTickerResponse>>() {
                            override fun onSuccess(tickerResponseList: List<UpbitTickerResponse>) {
                                val convertTickerList = tickerResponseList.map { tickerResponse ->
                                    tickerResponse.toTicker(onClick)
                                }
                                success(convertTickerList)
                            }

                            override fun onError(e: Throwable) {
                                e.message?.let(failed)
                            }
                        })
                }

                override fun onError(e: Throwable) {
                }
            })
        ++subscribeCount
    }

    override fun getTicker(
        baseCurrency: String,
        coinName: String,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        compositeDisposable += upbitRemoteDataSource.getTickerList("$baseCurrency-$coinName")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.map { tickerResponse ->
                    tickerResponse.toCompareTicker()
                }
            }
            .subscribeWith(object : DisposableSingleObserver<List<CompareTicker>>() {
                override fun onSuccess(convertTickerList: List<CompareTicker>) {
                    if (convertTickerList.isNotEmpty()){
                        success(convertTickerList[0])
                    }
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