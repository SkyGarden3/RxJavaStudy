package com.architecture.study.data.model

interface TickerProvider {
    fun toTicker(onClick: (ticker: Ticker) -> Unit, coinName: String = ""): Ticker

    fun toCompareTicker(coinName: String = ""): CompareTicker
}