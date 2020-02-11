package com.architecture.study.data.model

import android.os.Parcel
import android.os.Parcelable

data class CompareTicker(
    var baseCurrency: String = "",
    var coinName: String = "",
    var exchangeName: String = "",
    val nowPrice: String,
    var comparePrice: Double = 0.0,
    val transactionAmount: String
) : Parcelable {

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<CompareTicker> =
            object : Parcelable.Creator<CompareTicker> {
                override fun createFromParcel(source: Parcel): CompareTicker = CompareTicker(source)
                override fun newArray(size: Int): Array<CompareTicker?> = arrayOfNulls(size)
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