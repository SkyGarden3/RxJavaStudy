package com.architecture.study.data.repository

import com.architecture.study.data.Result
import com.architecture.study.data.Result.Success
import com.architecture.study.data.Result.Error
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.CoinoneRemoteDataSource
import com.architecture.study.ext.plusAssign
import com.architecture.study.network.model.coinone.CoinoneResponse
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class CoinoneRepository(
    private val coinoneRemoteDataSource: CoinoneRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    TickerRepository {

    override suspend fun getAllTicker(
        baseCurrency: String?,
        onClick: (ticker: Ticker) -> Unit
    ): Result<List<Ticker>> {

        return withContext(ioDispatcher) {
            val resultResponse = coinoneRemoteDataSource.getTickerList()

            (resultResponse as? Success)?.let {
                if (it.data["errorCode"] != "0") {
                    return@withContext Error(error(it.data["errorCode"].toString()))
                }

                val gson = Gson()

                val tickerList = it.data.filter {entry->
                    !listOf("result", "timestamp", "errorCode").contains(entry.key)
                }.map {(_, tickerResponse) ->
                    gson.fromJson(tickerResponse.toString(), CoinoneResponse::class.java)
                        .toTicker(onClick)
                }

                return@withContext Success(tickerList)
            } ?: run {
                return@withContext resultResponse as Error
            }

        }
    }

    override suspend fun getTicker(
        baseCurrency: String,
        coinName: String
    ): Result<CompareTicker> {
        return withContext(ioDispatcher) {
            val resultResponse =
                coinoneRemoteDataSource.getTicker(coinName)

            (resultResponse as? Success)?.let {
                return@withContext Success(it.data.toCompareTicker())
            } ?: run {
                return@withContext resultResponse as Error
            }

        }
    }

    override fun finish() {
    }
}