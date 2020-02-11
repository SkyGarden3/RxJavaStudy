package com.architecture.study.view.coin.model

import com.architecture.study.domain.model.Ticker

data class TickerItem(
    val coinName: String,
    val nowPrice: String,
    val compareYesterday: Double,
    val transactionAmount: String,
    val onClick: (ticker: TickerItem) -> Unit
) {

    companion object{
        fun of(ticker: Ticker, onClick: (ticker: TickerItem) -> Unit) =
            TickerItem(
                coinName = ticker.coinName,
                nowPrice = ticker.nowPrice,
                compareYesterday = ticker.compareYesterday,
                transactionAmount = ticker.transactionAmount,
                onClick = onClick
            )
    }
}