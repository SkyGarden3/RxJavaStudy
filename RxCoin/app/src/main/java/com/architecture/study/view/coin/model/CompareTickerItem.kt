package com.architecture.study.view.coin.model

import android.os.Parcel
import android.os.Parcelable
import com.architecture.study.domain.model.CompareTicker

data class CompareTickerItem(
    val baseCurrency: String = "",
    val coinName: String,
    val exchangeName: String = "",
    val nowPrice: String,
    var comparePrice: Double = 0.0,
    val transactionAmount: String
) : Parcelable {
    companion object {
        fun of(compareTicker: CompareTicker) =
            CompareTickerItem(
                baseCurrency = compareTicker.baseCurrency,
                coinName = compareTicker.coinName,
                exchangeName = compareTicker.exchangeName,
                nowPrice = compareTicker.nowPrice,
                comparePrice = compareTicker.comparePrice,
                transactionAmount = compareTicker.transactionAmount
            )

        fun of(tickerItem: TickerItem, baseCurrency: String, exchangeName: String) =
            CompareTickerItem(
                baseCurrency = baseCurrency,
                coinName = tickerItem.coinName,
                exchangeName = exchangeName,
                nowPrice = tickerItem.nowPrice,
                transactionAmount = tickerItem.transactionAmount
            )

        @JvmField
        val CREATOR: Parcelable.Creator<CompareTickerItem> =
            object : Parcelable.Creator<CompareTickerItem> {
                override fun createFromParcel(source: Parcel): CompareTickerItem =
                    CompareTickerItem(source)

                override fun newArray(size: Int): Array<CompareTickerItem?> = arrayOfNulls(size)
            }
    }

    constructor(source: Parcel) : this(
        source.readString().orEmpty(),
        source.readString().orEmpty(),
        source.readString().orEmpty(),
        source.readString().orEmpty(),
        source.readDouble(),
        source.readString().orEmpty()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(baseCurrency)
        writeString(coinName)
        writeString(exchangeName)
        writeString(nowPrice)
        writeDouble(comparePrice)
        writeString(transactionAmount)
    }
}