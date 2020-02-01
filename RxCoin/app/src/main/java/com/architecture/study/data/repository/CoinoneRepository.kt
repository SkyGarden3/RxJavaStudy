package com.architecture.study.data.repository

import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.CoinoneRemoteDataSource
import com.architecture.study.ext.plusAssign
import com.architecture.study.network.model.coinone.CoinoneResponse
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CoinoneRepository(private val coinoneRemoteDataSource: CoinoneRemoteDataSource) :
    TickerRepository {

    private val compositeDisposable = CompositeDisposable()
    private var subscribeCount = 0

    override fun getAllTicker(
        baseCurrency: String?,
        success: (tickers: List<Ticker>) -> Unit,
        failed: (errorCode: String) -> Unit,
        onClick: (ticker: Ticker) -> Unit
    ) {

        compositeDisposable += coinoneRemoteDataSource
            .getTickerList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                if (it["errorCode"] != "0") {
                    failed.invoke("")
                }
            }
            .filter {
                it["errorCode"] == "0"
            }
            .map {
                val gson = Gson()
                it.filter { entry ->
                    !listOf(
                        "result",
                        "timestamp",
                        "errorCode"
                    ).contains(entry.key)
                }.map { (_, tickerResponse) ->
                    gson.fromJson(tickerResponse.toString(), CoinoneResponse::class.java)
                        .toTicker(onClick)
                }
            }
            .subscribe({
                success.invoke(it)
            }, {
                failed.invoke(it.message.orEmpty())
            })
        ++subscribeCount
    }

    override fun getTicker(
        baseCurrency: String,
        coinName: String,
        success: (tickers: CompareTicker) -> Unit,
        failed: (errorCode: String) -> Unit
    ) {
        compositeDisposable += coinoneRemoteDataSource
            .getTicker(coinName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.toCompareTicker()
            }
            .subscribe({
               success.invoke(it)
            }, {
               failed.invoke(it.message.orEmpty())
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