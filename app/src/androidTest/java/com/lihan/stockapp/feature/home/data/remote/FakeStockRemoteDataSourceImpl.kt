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
import kotlinx.coroutines.delay

class FakeStockRemoteDataSourceImpl: StockRemoteDataSource{

    var isSuccess = true
    var dataErrorNetwork = DataError.Network.SERVER_ERROR
    var isNeedDelay = true

    override suspend fun getBwiBBuAll(): Result<List<Bwibbu>, DataError.Network> {
        if (isNeedDelay){
            delay(1000L)
        }
        return if(isSuccess){
            Result.Success(
                data = DumpData.bwibbuDtoList.map { it.toBwibbu() }
            )
        }else{
            Result.Error(dataErrorNetwork)
        }
    }

    override suspend fun getStockDayAvgAll(): Result<List<StockDayAvg>, DataError.Network> {
        if (isNeedDelay){
            delay(1000L)
        }
        return if(isSuccess){
            Result.Success(
                data = DumpData.stockDayAvgList.map { it.toStockDayAvg() }
            )
        }else{
            Result.Error(dataErrorNetwork)
        }
    }

    override suspend fun getStockDayAll(): Result<List<StockDay>, DataError.Network> {
        if (isNeedDelay){
            delay(1000L)
        }
        return if(isSuccess){
            Result.Success(
                data = DumpData.stockDayDtoList.map { it.toStockDay() }
            )
        }else{
            Result.Error(dataErrorNetwork)
        }
    }

}