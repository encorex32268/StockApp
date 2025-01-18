package com.lihan.stockapp.feature.home.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.lihan.stockapp.feature.core.designsystem.ui.theme.StockAppTheme
import com.lihan.stockapp.feature.home.data.remote.DumpData
import com.lihan.stockapp.feature.home.data.remote.FakeHomeRepositoryImpl
import com.lihan.stockapp.feature.home.data.remote.FakeStockRemoteDataSourceImpl
import com.lihan.stockapp.feature.home.domain.repository.HomeRepository
import com.lihan.stockapp.feature.home.presentation.model.StockUI
import com.lihan.stockapp.feature.home.presentation.testtags.TestTags
import org.junit.Rule
import org.junit.Test


class HomeScreenTest {

    private val stockUiList = getStockUiList()

    private fun getStockUiList(): List<StockUI> {
        val stockUiMap = hashMapOf<String,StockUI>()
        DumpData.bwibbuDtoList.forEach { bwibbu ->
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
        DumpData.stockDayDtoList.forEach {stockDay ->
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
        DumpData.stockDayAvgList.forEach {stockDayAvg ->
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



    private lateinit var homeRepository: HomeRepository

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun homeScreenComposeTestRule(state: HomeState) {
        composeTestRule.setContent {
            StockAppTheme {
                HomeScreen(
                    state = state
                )
            }
        }
    }

    @Test
    fun screenLoading(){
        homeScreenComposeTestRule(
            state = HomeState(
                isLoading = true
            )
        )
        composeTestRule.onNodeWithTag(TestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun screenApiFailedShowRetryButton(){
        homeScreenComposeTestRule(
            state = HomeState(
                isLoading = false,
                items = emptyList()
            )
        )
        composeTestRule.onNodeWithTag(TestTags.RETRY_BUTTON).assertIsDisplayed()
    }

    @Test
    fun screenApiFailedShowAlertDialog(){
        homeRepository = FakeHomeRepositoryImpl(
            stockRemoteDataSource = FakeStockRemoteDataSourceImpl().apply {
                isSuccess = false
                isNeedDelay = false
            }
        )
        composeTestRule.setContent {
            val viewModel = HomeViewModel(homeRepository)
            HomeScreenRoot(
                viewModel = viewModel
            )
        }
        composeTestRule.onNodeWithTag(TestTags.ERROR_ALERT_DIALOG).assertIsDisplayed()
    }

    @Test
    fun screenApiSucceedShowData(){
        homeScreenComposeTestRule(
            state = HomeState(
                isLoading = false,
                items = stockUiList
            )
        )
        composeTestRule.onNodeWithTag(TestTags.FILTER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.STOCK_LIST).assertIsDisplayed()
        val data  = stockUiList[0]
        composeTestRule.onNodeWithText(data.code!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.name!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.closingPrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.openingPrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.changePrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.transactions!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.tradeVolume!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.tradePrice!!).assertIsDisplayed()
    }

    @Test
    fun screenSearch(){
        val searchText = "1101"
        val searchStockUIList = stockUiList.filter { it.code == searchText }
        homeScreenComposeTestRule(
            state = HomeState(
                isLoading = false,
                items = searchStockUIList,
                searchText = "1101"
            )
        )
        composeTestRule.onNodeWithTag(TestTags.FILTER).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.STOCK_LIST).assertIsDisplayed()
        val data  = stockUiList.find { it.code == "1101" }!!
        composeTestRule.onNodeWithText(data.code!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.name!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.closingPrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.openingPrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.changePrice!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.transactions!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.tradeVolume!!).assertIsDisplayed()
        composeTestRule.onNodeWithText(data.tradePrice!!).assertIsDisplayed()

    }


}