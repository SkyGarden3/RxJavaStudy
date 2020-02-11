package com.architecture.study.data.repository

import com.architecture.study.util.Result
import com.architecture.study.util.Result.Success
import com.architecture.study.util.Result.Error
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.model.Ticker
import com.architecture.study.data.source.remote.CoinoneRemoteDataSource
import com.architecture.study.domain.repository.TickerRepository
import com.architecture.study.network.model.coinone.CoinoneResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class CoinoneRepository(
    private val coinoneRemoteDataSource: CoinoneRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    TickerRepository {

    override suspend fun getAllTicker(baseCurrency: String?): Result<List<Ticker>> {

        return withContext(ioDispatcher) {
            val resultResponse = coinoneRemoteDataSource.getTickerList()

            (resultResponse as? Success)?.let {
                if (it.data["errorCode"] != "0") {
                    return@withContext error(it.data["errorCode"].toString())
                }

                val gson = Gson()

                val tickerList = it.data.filter {entry->
                    !listOf("result", "timestamp", "errorCode").contains(entry.key)
                }.map {(_, tickerResponse) ->
                    gson.fromJson(tickerResponse.toString(), CoinoneResponse::class.java)
                        .toTicker()
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

}