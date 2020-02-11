package com.architecture.study.network.model.bithumb


import com.architecture.study.data.enums.Exchange
import com.architecture.study.data.model.CompareTicker
import com.architecture.study.data.model.Ticker
import com.architecture.study.data.model.TickerProvider
import com.architecture.study.ext.getAmount
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

data class BithumbTickerResponse(
    @SerializedName("acc_trade_value")
    val accTradeValue: String,
    @SerializedName("acc_trade_value_24H")
    val accTradeValue24H: Double,
    @SerializedName("closing_price")
    val closingPrice: Double,
    @SerializedName("date")
    val date: String,
    @SerializedName("fluctate_24H")
    val fluctate24H: String,
    @SerializedName("fluctate_rate_24H")
    val fluctateRate24H: Double,
    @SerializedName("max_price")
    val maxPrice: String,
    @SerializedName("min_price")
    val minPrice: String,
    @SerializedName("opening_price")
    val openingPrice: String,
    @SerializedName("prev_closing_price")
    val prevClosingPrice: Double,
    @SerializedName("units_traded")
    val unitsTraded: String,
    @SerializedName("units_traded_24H")
    val unitsTraded24H: String
) : TickerProvider {

    override fun toTicker(onClick: (ticker: Ticker) -> Unit) =
        Ticker(
            nowPrice = DecimalFormat("0.########").format(closingPrice),
            compareYesterday = fluctateRate24H,
            transactionAmount = getAmount(accTradeValue24H, "KRW"),
            onClick = onClick
        )

    override fun toCompareTicker(): CompareTicker =
        CompareTicker(
            exchangeName = Exchange.BITHUMB.exchangeName,
            nowPrice = DecimalFormat("0.########").format(closingPrice),
            transactionAmount = getAmount(accTradeValue24H, "KRW")
        )
}