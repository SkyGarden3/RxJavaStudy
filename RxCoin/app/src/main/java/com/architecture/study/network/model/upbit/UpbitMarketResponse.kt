package com.architecture.study.network.model.upbit

import com.google.gson.annotations.SerializedName

data class UpbitMarketResponse(
    @SerializedName("english_name")
    val englishName: String,
    @SerializedName("korean_name")
    val koreanName: String,
    @SerializedName("market")
    val market: String
)