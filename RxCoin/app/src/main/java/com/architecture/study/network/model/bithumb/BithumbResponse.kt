package com.architecture.study.network.model.bithumb


import com.google.gson.annotations.SerializedName

data class BithumbResponse(
    @SerializedName("data")
    val tickerResponse: Map<String, Any>,
    @SerializedName("status")
    val status: String
)