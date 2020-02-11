package com.architecture.study.data.model

interface TickerProvider {
    fun toTicker(onClick: (ticker: Ticker) -> Unit): Ticker

    fun toCompareTicker(): CompareTicker
}