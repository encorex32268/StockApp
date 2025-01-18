package com.lihan.stockapp.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockDayDto(
    @SerialName("Code")
    val code: String,
    @SerialName("Name")
    val name: String,
    @SerialName("TradeVolume")
    val tradeVolume: String,
    @SerialName("TradeValue")
    val tradePrice: String,
    @SerialName("OpeningPrice")
    val openingPrice: String,
    @SerialName("HighestPrice")
    val highestPrice: String,
    @SerialName("LowestPrice")
    val lowestPrice: String,
    @SerialName("ClosingPrice")
    val closingPrice: String,
    @SerialName("Change")
    val changePrice: String,
    @SerialName("Transaction")
    val transactions: String,
)
