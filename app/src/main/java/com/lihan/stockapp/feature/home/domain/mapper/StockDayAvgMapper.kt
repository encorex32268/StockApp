package com.lihan.stockapp.feature.home.domain.mapper

import com.lihan.stockapp.feature.home.data.model.StockDayAvgDto
import com.lihan.stockapp.feature.home.domain.model.StockDayAvg

fun StockDayAvgDto.toStockDayAvg(): StockDayAvg {
    return StockDayAvg(
        code = code,
        name = name,
        closingPrice = closingPrice,
        monthlyAvgPrice = monthlyAvgPrice
    )
}