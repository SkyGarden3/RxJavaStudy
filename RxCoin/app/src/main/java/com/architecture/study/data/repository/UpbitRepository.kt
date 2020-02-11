package com.architecture.study.data.repository

import com.architecture.study.data.Result
import com.architecture.study.data.Result.Error
import com.architecture.study.data.Result.Success
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.source.remote.UpbitRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNREACHABLE_CODE")
class UpbitRepository(
    private val upbitRemoteDataSource: UpbitRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TickerRepository {

    override suspend fun getAllTicker(
        baseCurrency: String?,
        onClick: (ticker: Ticker) -> Unit
    ): Result<List<Ticker>> {

        return withContext(ioDispatcher) {
            val marketResultResponse = upbitRemoteDataSource.getMarketList()

            (marketResultResponse as? Success)?.data?.let { marketResponseList ->
                val markets = marketResponseList.asSequence()
                    .map { it.market }
                    .filter {
                        it.split("-")[0] ==
                                baseCurrency
                    }
                    .toList()

                val tickerResultResponse =
                    upbitRemoteDataSource.getTickerList(markets.joinToString())

                (tickerResultResponse as? Success)?.data?.let { tickerResponseList ->
                    val convertTickerList = tickerResponseList.map { tickerResponse ->
                        tickerResponse.toTicker(onClick)
                    }
                    return@withContext Success(convertTickerList)
                } ?: run {
                    return@withContext tickerResultResponse as Error
                }
            } ?: run {
                return@withContext marketResultResponse as Error
            }
        }
    }

    override suspend fun getTicker(
        baseCurrency: String,
        coinName: String
    ): Result<CompareTicker> {
        return withContext(ioDispatcher) {
            val resultResponse =
                upbitRemoteDataSource.getTickerList("$baseCurrency-$coinName")

            (resultResponse as? Success)?.data?.let {
                if (it.isNotEmpty()) {
                    return@withContext Success(it[0].toCompareTicker())
                } else {
                    return@withContext error("empty")
                }

            } ?: run {
                return@withContext resultResponse as Error
            }

        }
    }
}