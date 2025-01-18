package com.lihan.stockapp.feature.home.domain.repository

import com.lihan.stockapp.feature.core.util.DataError
import com.lihan.stockapp.feature.core.util.Result
import com.lihan.stockapp.feature.home.domain.model.Bwibbu
import com.lihan.stockapp.feature.home.domain.model.StockDay
import com.lihan.stockapp.feature.home.domain.model.StockDayAvg

interface StockRemoteDataSource {
    suspend fun getBwiBBuAll(): Result<List<Bwibbu> , DataError.Network>
    suspend fun getStockDayAvgAll(): Result<List<StockDayAvg> , DataError.Network>
    suspend fun getStockDayAll(): Result<List<StockDay> , DataError.Network>
}