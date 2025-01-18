package com.lihan.stockapp.feature.home.domain.model


data class StockDayAvg(
    val code: String,
    val name: String,
    val closingPrice: String,
    val monthlyAvgPrice: String
)
