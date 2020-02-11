package com.architecture.study.ext

import com.architecture.study.domain.model.TickerProvider

fun TickerProvider.getAmount(accTradePrice24h: Double, marketName: String): String =
    when (marketName) {
        "KRW" -> {
            String.format("%,d", (accTradePrice24h / 1_000_000L).toInt()) + "M"
        }
        "BTC" -> {
            String.format("%,.3f", accTradePrice24h)
        }
        "USDT" -> {
            String.format("%,dk", accTradePrice24h.toInt())
        }

        else -> error("error")
    }