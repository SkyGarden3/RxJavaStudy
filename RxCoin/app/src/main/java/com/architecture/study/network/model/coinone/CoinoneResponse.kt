package com.architecture.study.network.model.coinone


import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.model.TickerProvider
import com.google.gson.annotations.SerializedName

data class CoinoneResponse(
    @SerializedName("currency")
    val currency: String,
    @SerializedName("first")
    val first: Double,
    @SerializedName("high")
    val high: Double,
    @SerializedName("last")
    val last: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("volume")
    val volume: Double,
    @SerializedName("yesterday_first")
    val yesterdayFirst: String,
    @SerializedName("yesterday_high")
    val yesterdayHigh: String,
    @SerializedName("yesterday_last")
    val yesterdayLast: String,
    @SerializedName("yesterday_low")
    val yesterdayLow: String,
    @SerializedName("yesterday_volume")
    val yesterdayVolume: String
) : TickerProvider {
    override fun toTicker(onClick: (ticker: Ticker) -> Unit, coinName: String): Ticker {
        val diff = (last - first) / first * 100
        val transactionAmount = String.format("%,d", ((volume * last) / 1_000_000L).toInt())
        return Ticker(
            currency.toUpperCase(),
            last.toInt().toString(),
            diff,
            "${transactionAmount}M",
            onClick
        )
    }

    override fun toCompareTicker(coinName: String): CompareTicker {
        val transactionAmount = String.format("%,d", ((volume * last) / 1_000_000L).toInt())
        return CompareTicker(
            coinName = currency,
            exchangeName = Exchange.COINONE.exchangeName,
            nowPrice = last.toInt().toString(),
            transactionAmount = "${transactionAmount}M"
        )
    }
}