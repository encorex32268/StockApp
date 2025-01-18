package com.lihan.stockapp.feature.home.presentation.model

data class StockUI(
    val code: String?=null,
    val name: String?=null,
    val openingPrice: String?=null,
    val closingPrice: String?=null,
    val highestPrice: String?=null,
    val lowestPrice: String?=null,
    val changePrice: String?=null,
    val monthlyAveragePrice: String?=null,
    val transactions: String?=null,
    val tradeVolume: String?=null,
    val tradePrice: String?=null,
    val priceEarningsRatio: String?=null,
    val dividendYield: String?=null,
    val priceToBookRatio: String?=null
)
