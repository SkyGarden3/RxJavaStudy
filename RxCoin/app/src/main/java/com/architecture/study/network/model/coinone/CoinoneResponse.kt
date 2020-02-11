package com.architecture.study.network.model.coinone


import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.model.TickerProvider
import com.architecture.study.ext.getAmount
import com.google.gson.annotations.SerializedName
import java.util.*

data class CoinoneResponse(
    @SerializedName("currency")
    val currency: String?,
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
    override fun toTicker(onClick: (ticker: Ticker) -> Unit) =
        Ticker(
            coinName = currency?.toUpperCase(Locale.US).orEmpty(),
            nowPrice = last.toInt().toString(),
            compareYesterday = (last - first) / first * 100,
            transactionAmount = getAmount(volume * last, "KRW"),
            onClick = onClick
        )


    override fun toCompareTicker() =
        CompareTicker(
            coinName = currency.orEmpty(),
            exchangeName = Exchange.COINONE.exchangeName,
            nowPrice = last.toInt().toString(),
            transactionAmount = getAmount(volume * last, "KRW")
        )

}