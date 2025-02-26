package com.lihan.stockapp.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockDayAvgDto(
    @SerialName("Code")
    val code: String,
    @SerialName("Name")
    val name: String,
    @SerialName("ClosingPrice")
    val closingPrice: String,
    @SerialName("MonthlyAveragePrice")
    val monthlyAvgPrice: String
)
