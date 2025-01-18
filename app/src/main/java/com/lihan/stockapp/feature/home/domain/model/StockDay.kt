package com.lihan.stockapp.feature.home.domain.model

data class StockDay(
    val code: String,
    val name: String,
    val tradeVolume: String,
    val tradePrice: String,
    val openingPrice: String,
    val highestPrice: String,
    val lowestPrice: String,
    val closingPrice: String,
    val changePrice: String,
    val transactions: String
)
