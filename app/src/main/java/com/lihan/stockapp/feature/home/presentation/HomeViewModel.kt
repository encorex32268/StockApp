package com.lihan.stockapp.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.stockapp.feature.core.presentation.toUiText
import com.lihan.stockapp.feature.core.util.Result
import com.lihan.stockapp.feature.core.util.map
import com.lihan.stockapp.feature.home.domain.model.Bwibbu
import com.lihan.stockapp.feature.home.domain.model.StockDay
import com.lihan.stockapp.feature.home.domain.model.StockDayAvg
import com.lihan.stockapp.feature.home.domain.repository.HomeRepository
import com.lihan.stockapp.feature.home.presentation.model.StockUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
): ViewModel() {

    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .asStateFlow()
        .onStart {
            fetchData()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private var currentStockUiList = emptyList<StockUI>()

    fun onAction(action: HomeAction){
        when(action){
            is HomeAction.OnSearchTextChange -> {
                if (action.text.isEmpty()) {
                    _state.update {
                        it.copy(
                            searchText = "",
                            items = currentStockUiList
                        )
                    }
                    return
                }else {
                    val isNumeric = action.text.all { it.isDigit() }
                    val searchedList = if (isNumeric){
                        currentStockUiList.filter { it.code == action.text }
                    }else{
                        currentStockUiList.filter { it.name?.contains(action.text) == true }
                    }

                    _state.update {
                        it.copy(
                            searchText = action.text,
                            items = searchedList
                        )
                    }
                }

            }
            HomeAction.ApiRetry -> {
                fetchData()
            }
            is HomeAction.OnStockClick -> {
                _state.update {
                    it.copy(
                        selectedStockUI = action.stockUI
                    )
                }
            }
            HomeAction.AscendingOrder -> {
                val stockUIList = state.value.items
                _state.update {
                    it.copy(
                        items = stockUIList.sortedBy { it.code }
                    )
                }
            }
            HomeAction.DescendingOrder ->{
                val stockUIList = state.value.items
                _state.update {
                    it.copy(
                        items = stockUIList.sortedByDescending { it.code }
                    )
                }
            }
        }
    }


    private fun fetchData() {
        viewModelScope.launch(Dispatchers.IO){
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            val fetchBwiBBuAll =  flow { emit(repository.getBwiBBuAll()) }
            val fetchStockDayAvg = flow {  emit(repository.getStockDayAvgAll())}
            val fetchStockDay = flow { emit(repository.getStockDayAll())}

            combine(
                fetchBwiBBuAll,
                fetchStockDayAvg,
                fetchStockDay
            ){ bwibbuResult,stockDayAvgResult,stockDayResult ->

                val isBwibbuSucceed = bwibbuResult is Result.Success
                val isStockDayAvgSucceed = stockDayAvgResult is Result.Success
                val isStockDaySucceed = stockDayResult is Result.Success

                if (isBwibbuSucceed && isStockDayAvgSucceed && isStockDaySucceed) {
                    val bwibbu = bwibbuResult as Result.Success
                    val stockDayAvg = stockDayAvgResult as Result.Success
                    val stockDay = stockDayResult as Result.Success
                    val stockUiList = updateStockUiList(
                        bwibbuList = bwibbu.data,
                        stockDayAvgList = stockDayAvg.data,
                        stockDayList = stockDay.data
                    )
                    currentStockUiList = stockUiList
                    _state.update {
                        it.copy(
                            items = stockUiList
                        )
                    }
                }else{
                    when{
                        bwibbuResult is Result.Error -> {
                            _uiEvent.send(
                                HomeUiEvent.ErrorMessage(
                                    errorMessage = bwibbuResult.error.toUiText()
                                )
                            )
                        }
                        stockDayAvgResult is Result.Error -> {
                            _uiEvent.send(
                                HomeUiEvent.ErrorMessage(
                                    errorMessage = stockDayAvgResult.error.toUiText()
                                )
                            )
                        }
                        stockDayResult is Result.Error -> {
                            _uiEvent.send(
                                HomeUiEvent.ErrorMessage(
                                    errorMessage = stockDayResult.error.toUiText()
                                )
                            )
                        }
                    }
                }
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun updateStockUiList(
        bwibbuList: List<Bwibbu>,
        stockDayList: List<StockDay>,
        stockDayAvgList: List<StockDayAvg>
    ): List<StockUI>{
        val stockUiMap = state.value.items.associateBy { it.code }.toMutableMap()
        bwibbuList.forEach { bwibbu ->
            val stockUI = stockUiMap[bwibbu.code]
            stockUiMap[bwibbu.code] = stockUI?.copy(
                name = bwibbu.name,
                priceToBookRatio = bwibbu.priceToBookRatio,
                priceEarningsRatio = bwibbu.priceEarningsRatio,
                dividendYield = bwibbu.dividendYield
            ) ?: StockUI(
                code = bwibbu.code,
                name = bwibbu.name,
                priceToBookRatio = bwibbu.priceToBookRatio,
                priceEarningsRatio = bwibbu.priceEarningsRatio,
                dividendYield = bwibbu.dividendYield
            )
        }
        stockDayList.forEach {stockDay ->
            val stockUI = stockUiMap[stockDay.code]
            stockUiMap[stockDay.code] = stockUI?.copy(
                tradePrice = stockDay.tradePrice,
                tradeVolume = stockDay.tradeVolume,
                openingPrice = stockDay.openingPrice,
                highestPrice = stockDay.highestPrice,
                lowestPrice = stockDay.lowestPrice,
                transactions = stockDay.transactions,
                closingPrice = stockDay.closingPrice,
                changePrice = stockDay.changePrice
            )?: StockUI(
                code = stockDay.code,
                name = stockDay.name,
                tradePrice = stockDay.tradePrice,
                tradeVolume = stockDay.tradeVolume,
                openingPrice = stockDay.openingPrice,
                highestPrice = stockDay.highestPrice,
                lowestPrice = stockDay.lowestPrice,
                transactions = stockDay.transactions,
                closingPrice = stockDay.closingPrice,
                changePrice = stockDay.changePrice
            )
        }
        stockDayAvgList.forEach {stockDayAvg ->
            val stockUI = stockUiMap[stockDayAvg.code]
            stockUiMap[stockDayAvg.code] = stockUI?.copy(
                code = stockDayAvg.code,
                name = stockDayAvg.name,
                closingPrice = stockDayAvg.closingPrice,
                monthlyAveragePrice = stockDayAvg.monthlyAvgPrice
            )?:  StockUI(
                code = stockDayAvg.code,
                name = stockDayAvg.name,
                closingPrice = stockDayAvg.closingPrice,
                monthlyAveragePrice = stockDayAvg.monthlyAvgPrice
            )
        }
        return stockUiMap.values.toList().sortedBy { it.code }
    }


}
