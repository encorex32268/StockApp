package com.lihan.stockapp.feature.home.presentation

import com.lihan.stockapp.feature.home.presentation.model.StockUI

data class HomeState(
    val isLoading: Boolean = false,
    val items: List<StockUI> = emptyList(),
    val searchText: String = "",
    val selectedStockUI: StockUI?=null
)
