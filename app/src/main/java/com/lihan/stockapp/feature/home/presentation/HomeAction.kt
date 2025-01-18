package com.lihan.stockapp.feature.home.presentation

import com.lihan.stockapp.feature.home.presentation.model.StockUI

sealed interface HomeAction {
    data object ApiRetry: HomeAction
    data object DescendingOrder: HomeAction
    data object AscendingOrder: HomeAction
    data class OnStockClick(val stockUI: StockUI?): HomeAction
    data class OnSearchTextChange(val text: String): HomeAction
}