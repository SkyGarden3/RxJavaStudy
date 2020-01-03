package com.architecture.study.data.model

data class Ticker(
    val coinName: String,
    val nowPrice: String,
    val compareYesterday: Double,
    val transactionAmount: String,
    val onClick: (ticker: Ticker) -> Unit
): TickerProvider{
    override fun toTicker(onClick: (ticker: Ticker) -> Unit, coinName: String): Ticker =
        this


    override fun toCompareTicker(basePrice: Double, coinName: String): CompareTicker =
        CompareTicker(
            coinName = this.coinName,
            nowPrice = nowPrice,
            comparePrice = nowPrice.replace(",", "").toDouble() - basePrice,
            transactionAmount = transactionAmount
        )

}