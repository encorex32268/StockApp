package com.lihan.stockapp.feature.home.data.remote

import com.lihan.stockapp.feature.core.network.get
import com.lihan.stockapp.feature.core.util.DataError
import com.lihan.stockapp.feature.core.util.Result
import com.lihan.stockapp.feature.core.util.map
import com.lihan.stockapp.feature.home.data.model.BwibbuDto
import com.lihan.stockapp.feature.home.data.model.StockDayAvgDto
import com.lihan.stockapp.feature.home.data.model.StockDayDto
import com.lihan.stockapp.feature.home.domain.mapper.toBwibbu
import com.lihan.stockapp.feature.home.domain.mapper.toStockDay
import com.lihan.stockapp.feature.home.domain.mapper.toStockDayAvg
import com.lihan.stockapp.feature.home.domain.model.Bwibbu
import com.lihan.stockapp.feature.home.domain.model.StockDay
import com.lihan.stockapp.feature.home.domain.model.StockDayAvg
import com.lihan.stockapp.feature.home.domain.repository.StockRemoteDataSource
import io.ktor.client.HttpClient

data class StockRemoteDataSourceImpl(
    private val httpClient: HttpClient
): StockRemoteDataSource{

    companion object{
        private const val BWIBBU_API = "/exchangeReport/BWIBBU_ALL"
        private const val STOCK_DAY_AVG_API = "/exchangeReport/STOCK_DAY_AVG_ALL"
        private const val STOCK_DAY_API = "/exchangeReport/STOCK_DAY_ALL"
    }

    override suspend fun getBwiBBuAll(): Result<List<Bwibbu>, DataError.Network> {
       return httpClient.get<List<BwibbuDto>>(
           route = BWIBBU_API
       ).map { dtos ->
           dtos.map { dto ->
               dto.toBwibbu()
           }
       }
    }

    override suspend fun getStockDayAvgAll(): Result<List<StockDayAvg>, DataError.Network> {
        return httpClient.get<List<StockDayAvgDto>>(
            route = STOCK_DAY_AVG_API
        ).map { dtos ->
            dtos.map { dto ->
                dto.toStockDayAvg()
            }
        }
    }

    override suspend fun getStockDayAll(): Result<List<StockDay>, DataError.Network> {
        return httpClient.get<List<StockDayDto>>(
            route = STOCK_DAY_API
        ).map { dtos ->
            dtos.map { dto ->
                dto.toStockDay()
            }
        }
    }

}