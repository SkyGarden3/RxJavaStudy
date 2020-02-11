package com.architecture.study.domain.model

import java.util.*

data class CompareTicker(
    val uuid: String = UUID.randomUUID().toString(),
    var baseCurrency: String = "",
    var coinName: String = "",
    var exchangeName: String = "",
    val nowPrice: String,
    var comparePrice: Double = 0.0,
    val transactionAmount: String
)