package com.lihan.stockapp.feature.home.domain.mapper

import com.lihan.stockapp.feature.home.data.model.BwibbuDto
import com.lihan.stockapp.feature.home.domain.model.Bwibbu

fun BwibbuDto.toBwibbu(): Bwibbu {
    return Bwibbu(
        code = code,
        name = name,
        priceEarningsRatio = priceEarningsRatio,
        priceToBookRatio = priceToBookRatio,
        dividendYield = dividendYield
    )
}