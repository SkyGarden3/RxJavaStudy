package com.architecture.study.network.model.coinone


import com.architecture.study.util.enums.Exchange
import com.architecture.study.domain.model.CompareTicker
import com.architecture.study.domain.model.Ticker
import com.architecture.study.domain.model.TickerProvider
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
    override fun toTicker() =
        Ticker(
            coinName = currency?.toUpperCase(Locale.US).orEmpty(),
            nowPrice = last.toInt().toString(),
            compareYesterday = (last - first) / first * 100,
            transactionAmount = getAmount(volume * last, "KRW")
        )


    override fun toCompareTicker() =
        CompareTicker(
            coinName = currency.orEmpty(),
            exchangeName = Exchange.COINONE.exchangeName,
            nowPrice = last.toInt().toString(),
            transactionAmount = getAmount(volume * last, "KRW")
        )

}