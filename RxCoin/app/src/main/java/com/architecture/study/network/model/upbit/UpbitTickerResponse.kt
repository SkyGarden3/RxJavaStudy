package com.architecture.study.network.model.upbit

import android.util.Log
import com.architecture.study.R
import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.model.TickerProvider
import com.architecture.study.ext.getAmount
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat
import kotlin.math.ulp


data class UpbitTickerResponse(
    @SerializedName("acc_trade_price")
    val accTradePrice: Double,
    @SerializedName("acc_trade_price_24h")
    val accTradePrice24h: Double,
    @SerializedName("acc_trade_volume")
    val accTradeVolume: Double,
    @SerializedName("acc_trade_volume_24h")
    val accTradeVolume24h: Double,
    @SerializedName("change")
    val change: String,
    @SerializedName("change_price")
    val changePrice: Double,
    @SerializedName("change_rate")
    val changeRate: Double,
    @SerializedName("high_price")
    val highPrice: Double,
    @SerializedName("highest_52_week_date")
    val highest52WeekDate: String,
    @SerializedName("highest_52_week_price")
    val highest52WeekPrice: Double,
    @SerializedName("low_price")
    val lowPrice: Double,
    @SerializedName("lowest_52_week_date")
    val lowest52WeekDate: String,
    @SerializedName("lowest_52_week_price")
    val lowest52WeekPrice: Double,
    @SerializedName("market")
    val market: String,
    @SerializedName("opening_price")
    val openingPrice: Double,
    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double,
    @SerializedName("signed_change_price")
    val signedChangePrice: Double,
    @SerializedName("signed_change_rate")
    val signedChangeRate: Double,
    @SerializedName("timestamp")
    val timestamp: Long,
    @SerializedName("trade_date")
    val tradeDate: String,
    @SerializedName("trade_date_kst")
    val tradeDateKst: String,
    @SerializedName("trade_price")
    val tradePrice: Double,
    @SerializedName("trade_time")
    val tradeTime: String,
    @SerializedName("trade_time_kst")
    val tradeTimeKst: String,
    @SerializedName("trade_timestamp")
    val tradeTimestamp: Long,
    @SerializedName("trade_volume")
    val tradeVolume: Double
): TickerProvider {

    override fun toTicker(onClick: (ticker: Ticker) -> Unit) =
        Ticker(
            coinName = market.split("-")[1],
            nowPrice = DecimalFormat("0.########").format(tradePrice),
            compareYesterday = ((tradePrice / prevClosingPrice) - 1) * 100,
            transactionAmount = getAmount(accTradePrice24h, market.split("-")[0]),
            onClick = onClick
        )

    override fun toCompareTicker() =
        CompareTicker(
            coinName = market.split("-")[1],
            exchangeName = Exchange.UPBIT.exchangeName,
            nowPrice = DecimalFormat("0.########").format(tradePrice),
            transactionAmount = getAmount(accTradePrice24h, market.split("-")[0])
        )
}