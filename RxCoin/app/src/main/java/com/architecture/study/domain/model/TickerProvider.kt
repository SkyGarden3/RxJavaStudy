package com.architecture.study.domain.model

interface TickerProvider {
    fun toTicker(): Ticker
    fun toCompareTicker(): CompareTicker
}
