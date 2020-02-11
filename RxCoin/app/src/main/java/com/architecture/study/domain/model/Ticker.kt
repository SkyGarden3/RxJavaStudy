package com.architecture.study.domain.model

import java.util.*

data class Ticker(
    val uuid: String = UUID.randomUUID().toString(),
    var coinName: String = "",
    val nowPrice: String,
    val compareYesterday: Double,
    val transactionAmount: String
) : TickerProvider {
    override fun toTicker(): Ticker =
        this

    override fun toCompareTicker(): CompareTicker =
        CompareTicker(
            coinName = coinName,
            nowPrice = nowPrice,
            transactionAmount = transactionAmount
        )
}