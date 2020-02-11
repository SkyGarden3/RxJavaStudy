package com.architecture.study.util.enums

enum class Exchange(val exchangeName: String, val baseCurrencies: List<String>) {
    UPBIT("Upbit", listOf("KRW", "BTC", "USDT")),
    BITHUMB("Bithumb", listOf("KRW")),
    COINONE("Coinone", listOf("KRW"))
}

fun getBaseCurrencies(exchangeName: String): List<String>? {
    return Exchange.values().find { it.exchangeName == exchangeName }?.baseCurrencies
}





