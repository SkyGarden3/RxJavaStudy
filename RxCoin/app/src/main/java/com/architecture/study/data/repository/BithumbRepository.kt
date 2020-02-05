package com.architecture.study.data.repository

import com.architecture.study.data.Result
import com.architecture.study.data.Result.Error
import com.architecture.study.data.Result.Success
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.BithumbRemoteDataSource
import com.architecture.study.network.model.bithumb.BithumbTickerResponse
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class BithumbRepository(
    private val bithumbRemoteDataSource: BithumbRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    TickerRepository {

    override suspend fun getAllTicker(
        baseCurrency: String?,
        onClick: (ticker: Ticker) -> Unit
    ): Result<List<Ticker>> {


        return withContext(ioDispatcher) {
            // Respond immediately with cache if available
            val resultResponse = bithumbRemoteDataSource.getTickerList()

            // Refresh the cache with the new tasks
            (resultResponse as? Success)?.let {
                val gson = Gson()
                val tickerList = it.data.tickerResponse
                    .filter { it.key != "date" }
                    .map { (currency, tickerResponse) ->
                        gson.fromJson(
                            tickerResponse.toString(),
                            BithumbTickerResponse::class.java
                        )
                            .toTicker(onClick, currency)
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
                bithumbRemoteDataSource.getTicker(
                    orderCurrency = coinName,
                    paymentCurrency = baseCurrency
                )

            (resultResponse as? Success)?.data?.let {
                return@withContext Success(it.tickerResponse.toCompareTicker(coinName))
            } ?: run {
                return@withContext resultResponse as Error
            }

        }
    }

    override fun finish() {
    }
}