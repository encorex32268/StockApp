package com.lihan.stockapp.feature.home.data.remote

import com.lihan.stockapp.feature.core.util.DataError
import com.lihan.stockapp.feature.core.util.Result
import com.lihan.stockapp.feature.home.domain.model.Bwibbu
import com.lihan.stockapp.feature.home.domain.model.StockDay
import com.lihan.stockapp.feature.home.domain.model.StockDayAvg
import com.lihan.stockapp.feature.home.domain.repository.HomeRepository
import com.lihan.stockapp.feature.home.domain.repository.StockRemoteDataSource

class FakeHomeRepositoryImpl(
    private val stockRemoteDataSource: StockRemoteDataSource
): HomeRepository {

    override suspend fun getBwiBBuAll(): Result<List<Bwibbu>, DataError.Network> {
        return stockRemoteDataSource.getBwiBBuAll()
    }

    override suspend fun getStockDayAvgAll(): Result<List<StockDayAvg>, DataError.Network> {
        return stockRemoteDataSource.getStockDayAvgAll()
    }

    override suspend fun getStockDayAll(): Result<List<StockDay>, DataError.Network> {
        return stockRemoteDataSource.getStockDayAll()
    }

}