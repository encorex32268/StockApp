package com.lihan.stockapp.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BwibbuDto(
    @SerialName("Code")
    val code: String,
    @SerialName("Name")
    val name: String,
    @SerialName("PEratio")
    val priceEarningsRatio: String,
    @SerialName("DividendYield")
    val dividendYield: String,
    @SerialName("PBratio")
    val priceToBookRatio: String
)
