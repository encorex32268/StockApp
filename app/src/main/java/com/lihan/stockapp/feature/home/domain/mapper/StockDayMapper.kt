package com.lihan.stockapp.feature.home.domain.mapper

import com.lihan.stockapp.feature.home.data.model.StockDayDto
import com.lihan.stockapp.feature.home.domain.model.StockDay

fun StockDayDto.toStockDay(): StockDay {
    return StockDay(
        code = code,
        name = name,
        tradeVolume = tradeVolume,
        tradePrice = tradePrice,
        openingPrice = openingPrice,
        highestPrice = highestPrice,
        lowestPrice = lowestPrice,
        closingPrice = closingPrice,
        changePrice = changePrice,
        transactions = transactions
    )
}