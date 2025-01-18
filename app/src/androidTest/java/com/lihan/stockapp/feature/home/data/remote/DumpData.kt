package com.lihan.stockapp.feature.home.data.remote

import com.lihan.stockapp.feature.home.data.model.BwibbuDto
import com.lihan.stockapp.feature.home.data.model.StockDayAvgDto
import com.lihan.stockapp.feature.home.data.model.StockDayDto
import com.lihan.stockapp.feature.home.presentation.model.StockUI

object DumpData {
    val bwibbuDtoList = listOf(
        BwibbuDto(
            code = "1101",
            name = "台泥",
            priceEarningsRatio = "25.88",
            priceToBookRatio = "0.96",
            dividendYield = "3.25"
        ),
        BwibbuDto(
            code = "1102",
            name = "亞泥",
            priceEarningsRatio = "13.72",
            priceToBookRatio = "0.81",
            dividendYield = "5.20"
        ),
        BwibbuDto(
            code = "1103",
            name = "嘉泥",
            priceEarningsRatio = "23.71",
            priceToBookRatio = "0.50",
            dividendYield = "3.01"
        ),
        BwibbuDto(
            code = "1104",
            name = "環泥",
            priceEarningsRatio = "12.13",
            priceToBookRatio = "0.82",
            dividendYield = "7.23"
        ),
        BwibbuDto(
            code = "1108",
            name = "幸福",
            priceEarningsRatio = "13.70",
            priceToBookRatio = "1.14",
            dividendYield = "7.02"
        ),
        BwibbuDto(
            code = "1109",
            name = "信大",
            priceEarningsRatio = "9.83",
            priceToBookRatio = "0.71",
            dividendYield = "7.02"
        ),
        BwibbuDto(
            code = "1110",
            name = "東泥",
            priceEarningsRatio = "40.20",
            priceToBookRatio = "1.20",
            dividendYield = "1.02"
        ),
        BwibbuDto(
            code = "1201",
            name = "味全",
            priceEarningsRatio = "21.40",
            priceToBookRatio = "1.16",
            dividendYield = "1.54"
        ),
        BwibbuDto(
            code = "1203",
            name = "味王",
            priceEarningsRatio = "18.22",
            priceToBookRatio = "1.46",
            dividendYield = "2.99"
        ),
        BwibbuDto(
            code = "1210",
            name = "大成",
            priceEarningsRatio = "12.79",
            priceToBookRatio = "1.78",
            dividendYield = "4.29"
        )
    )
    val stockDayAvgList = listOf(
        StockDayAvgDto(
            code = "0050",
            name = "元大台灣50",
            closingPrice = "194.10",
            monthlyAvgPrice = "198.01"
        ),
        StockDayAvgDto(
            code = "0051",
            name = "元大中型100",
            closingPrice = "74.65",
            monthlyAvgPrice = "76.29"
        ),
        StockDayAvgDto(
            code = "0052",
            name = "富邦科技",
            closingPrice = "192.50",
            monthlyAvgPrice = "196.49"
        ),
        StockDayAvgDto(
            code = "0053",
            name = "元大電子",
            closingPrice = "101.70",
            monthlyAvgPrice = "104.46"
        ),
        StockDayAvgDto(
            code = "0055",
            name = "元大MSCI金融",
            closingPrice = "28.00",
            monthlyAvgPrice = "28.14"
        ),
        StockDayAvgDto(
            code = "0056",
            name = "元大高股息",
            closingPrice = "36.12",
            monthlyAvgPrice = "36.71"
        ),
        StockDayAvgDto(
            code = "0057",
            name = "富邦摩台",
            closingPrice = "141.65",
            monthlyAvgPrice = "144.91"
        ),
        StockDayAvgDto(
            code = "0061",
            name = "元大寶滬深",
            closingPrice = "18.82",
            monthlyAvgPrice = "18.71"
        ),
        StockDayAvgDto(
            code = "006203",
            name = "元大MSCI台灣",
            closingPrice = "90.65",
            monthlyAvgPrice = "92.66"
        ),
        StockDayAvgDto(
            code = "006204",
            name = "永豐臺灣加權",
            closingPrice = "113.20",
            monthlyAvgPrice = "115.53"
        )
    )
    val stockDayDtoList = listOf(
        StockDayDto(
            code = "0050",
            name = "元大台灣50",
            tradeVolume = "18681935",
            tradePrice = "3630590932",
            openingPrice = "196.00",
            highestPrice = "196.00",
            lowestPrice = "193.75",
            closingPrice = "194.10",
            changePrice = "-1.5000",
            transactions = "32605"
        ),
        StockDayDto(
            code = "0051",
            name = "元大中型100",
            tradeVolume = "99244",
            tradePrice = "7451776",
            openingPrice = "75.15",
            highestPrice = "75.45",
            lowestPrice = "74.65",
            closingPrice = "74.65",
            changePrice = "-0.4000",
            transactions = "370"
        ),
        StockDayDto(
            code = "0052",
            name = "富邦科技",
            tradeVolume = "886285",
            tradePrice = "170100333",
            openingPrice = "193.45",
            highestPrice = "193.45",
            lowestPrice = "191.00",
            closingPrice = "192.50",
            changePrice = "-0.9500",
            transactions = "2058"
        ),
        StockDayDto(
            code = "0053",
            name = "元大電子",
            tradeVolume = "10562",
            tradePrice = "1078066",
            openingPrice = "102.15",
            highestPrice = "102.60",
            lowestPrice = "101.70",
            closingPrice = "101.70",
            changePrice = "-1.2000",
            transactions = "131"
        ),
        StockDayDto(
            code = "0055",
            name = "元大MSCI金融",
            tradeVolume = "95987",
            tradePrice = "2701210",
            openingPrice = "27.99",
            highestPrice = "28.25",
            lowestPrice = "27.99",
            closingPrice = "28.00",
            changePrice = "0.0100",
            transactions = "240"
        ),
        StockDayDto(
            code = "0056",
            name = "元大高股息",
            tradeVolume = "80983415",
            tradePrice = "2936107821",
            openingPrice = "36.43",
            highestPrice = "36.45",
            lowestPrice = "36.08",
            closingPrice = "36.12",
            changePrice = "-0.2400",
            transactions = "55359"
        ),
        StockDayDto(
            code = "0057",
            name = "富邦摩台",
            tradeVolume = "30326",
            tradePrice = "4307125",
            openingPrice = "142.25",
            highestPrice = "142.35",
            lowestPrice = "141.65",
            closingPrice = "141.65",
            changePrice = "-1.5000",
            transactions = "112"
        ),
        StockDayDto(
            code = "0061",
            name = "元大寶滬深",
            tradeVolume = "189257",
            tradePrice = "3568663",
            openingPrice = "18.85",
            highestPrice = "18.92",
            lowestPrice = "18.81",
            closingPrice = "18.82",
            changePrice = "-0.0100",
            transactions = "145"
        ),
        StockDayDto(
            code = "006203",
            name = "元大MSCI台灣",
            tradeVolume = "7271",
            tradePrice = "660045",
            openingPrice = "90.75",
            highestPrice = "90.75",
            lowestPrice = "90.60",
            closingPrice = "90.65",
            changePrice = "-0.9000",
            transactions = "110"
        ),
        StockDayDto(
            code = "006204",
            name = "永豐臺灣加權",
            tradeVolume = "5285",
            tradePrice = "599857",
            openingPrice = "114.45",
            highestPrice = "114.45",
            lowestPrice = "113.20",
            closingPrice = "113.20",
            changePrice = "-0.9000",
            transactions = "114"
        )
    )

}
