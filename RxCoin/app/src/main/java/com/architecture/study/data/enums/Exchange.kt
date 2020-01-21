package com.architecture.study.data.enums

enum class Exchange(val exchangeName: String, val baseCurrencies: List<String>) {
    UPBIT("Upbit", listOf("KRW", "BTC", "USDT")),
    BITHUMB("Bithumb", listOf("KRW"))
}





