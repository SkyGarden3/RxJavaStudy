package com.architecture.study.data.model

data class Ticker(
    var coinName: String = "",
    val nowPrice: String,
    val compareYesterday: Double,
    val transactionAmount: String,
    val onClick: (ticker: Ticker) -> Unit
) : TickerProvider {
    override fun toTicker(onClick: (ticker: Ticker) -> Unit): Ticker =
        this


    override fun toCompareTicker(): CompareTicker =
        CompareTicker(
            coinName = coinName,
            nowPrice = nowPrice,
            transactionAmount = transactionAmount
        )

}