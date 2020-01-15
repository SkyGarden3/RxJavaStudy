package com.architecture.study.network.model.bithumb

import com.google.gson.annotations.SerializedName

data class BithumbSingleResponse(

    @SerializedName("data")
    val tickerResponse: BithumbTickerResponse,
    @SerializedName("status")
    val status: String
)