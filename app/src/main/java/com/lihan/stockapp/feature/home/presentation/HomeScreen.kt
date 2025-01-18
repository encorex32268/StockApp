@file:OptIn(ExperimentalMaterial3Api::class)

package com.lihan.stockapp.feature.home.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lihan.stockapp.R
import com.lihan.stockapp.feature.core.designsystem.ui.theme.StockAppTheme
import com.lihan.stockapp.feature.home.presentation.components.PriceText
import com.lihan.stockapp.feature.home.presentation.components.StockItem
import com.lihan.stockapp.feature.home.presentation.components.StockSearchBar
import com.lihan.stockapp.feature.home.presentation.model.StockUI
import com.lihan.stockapp.feature.home.presentation.testtags.TestTags
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel
import java.util.Locale
import kotlin.random.Random

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var errorUiText by remember {
        mutableStateOf<String?>(null)
    }
    LaunchedEffect(viewModel){
        viewModel.uiEvent.collectLatest {
            when(it){
                is HomeUiEvent.ErrorMessage -> {
                    errorUiText = it.errorMessage.asString(context)
                }
            }
        }
    }
    HomeScreen(
        state = state,
        onAction = viewModel::onAction
    )
    errorUiText?.let {  errorMsg ->
        AlertDialog(
            modifier = Modifier.testTag(
                TestTags.ERROR_ALERT_DIALOG
            ),
            title = {
                Text(
                    text = stringResource(R.string.error_title)
                )
            },
            text = {
                Text(
                    text = errorMsg
                )
            },
            onDismissRequest = {
                errorUiText = null
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        errorUiText = null
                    }
                ) {
                    Text(stringResource(R.string.dialog_ok_button))
                }
            }
        )
    }
}

@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit = {}
){
    var isShowBottomSheet by remember {
        mutableStateOf(false)
    }
    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(36.dp)
                        .testTag(TestTags.LOADING),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
        state.items.isEmpty() && !state.isLoading  && state.searchText.isEmpty()-> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Button(
                    modifier = Modifier.testTag(TestTags.RETRY_BUTTON),
                    onClick = {
                        onAction(HomeAction.ApiRetry)
                    }
                ) {
                    Text(
                        text = stringResource(R.string.retry)
                    )
                }
            }
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .align(Alignment.End)
                        .testTag(TestTags.FILTER)
                    ,
                    onClick = {
                        isShowBottomSheet = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                StockSearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .testTag(TestTags.SEARCH_BAR),
                    text = state.searchText,
                    onValueChange = {
                        onAction(HomeAction.OnSearchTextChange(it))
                    }
                )
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .testTag(TestTags.STOCK_LIST),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(
                        state.items
                    ){ stockUI ->
                        StockItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            stockUI = stockUI,
                            onItemClick = {
                                onAction(HomeAction.OnStockClick(stockUI))
                            }
                        )
                    }
                }

            }
        }
    }
    if (isShowBottomSheet){
        ModalBottomSheet(
            modifier = Modifier.testTag(TestTags.FILTER_BOTTOM_SHEET),
            onDismissRequest = {
                isShowBottomSheet = false
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(HomeAction.DescendingOrder)
                            isShowBottomSheet = false
                        }
                        .testTag(TestTags.FILTER_DES)
                    ,
                    text = stringResource(R.string.descending_order),
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(HomeAction.AscendingOrder)
                            isShowBottomSheet = false
                        }
                        .testTag(TestTags.FILTER_ASC)
                    ,
                    text = stringResource(R.string.ascending_order),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
    state.selectedStockUI?.let {
        AlertDialog(
            modifier = Modifier.testTag(TestTags.ALERTDIALOG),
            onDismissRequest = {
                onAction(HomeAction.OnStockClick(null))
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    Text(
                        modifier = Modifier
                            .alignByBaseline()
                            .testTag(TestTags.ALERTDIALOG_CODE)
                        ,
                        text = it.code?:""
                    )
                    Text(
                        modifier = Modifier
                            .testTag(TestTags.ALERTDIALOG_NAME),
                        text = it.name?:""
                    )
                }
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ){
                    PriceText(
                        modifier = Modifier.fillMaxWidth().testTag(TestTags.ALERTDIALOG_PRICE_EARNINGS_RATIO),
                        title = stringResource(R.string.price_earnings_ratio),
                        valueText = it.priceEarningsRatio?:""
                    )
                    PriceText(
                        modifier = Modifier.fillMaxWidth().testTag(TestTags.ALERTDIALOG_DIVIDEND_YIELD),
                        title = stringResource(R.string.dividend_yield),
                        valueText = it.dividendYield?:""
                    )
                    PriceText(
                        modifier = Modifier.fillMaxWidth().testTag(TestTags.ALERTDIALOG_PRICE_TO_BOOK_RATIO),
                        title = stringResource(R.string.price_to_book_ratio),
                        valueText = it.priceToBookRatio?:""
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onAction(HomeAction.OnStockClick(null))
                    }
                ) {
                    Text(
                        modifier = Modifier.testTag(TestTags.ALERTDIALOG_OK_BUTTON),
                        text = stringResource(R.string.dialog_ok_button)
                    )
                }
            }
        )
    }

}

@Composable
@PreviewLightDark
private fun HomeScreenPreview(){
    val items = (0..10).map {
        val randDouble = Random.nextDouble(1.00, 9.99)
        val highestPrice = (1 + randDouble)
        val lowestPrice = (10 - randDouble)
        StockUI(
            code = Random.nextInt().toString(),
            name = "Stock ${it}",
            openingPrice = String.format(locale = Locale.getDefault(),"%.2f",randDouble),
            closingPrice = String.format(locale = Locale.getDefault(),"%.2f",randDouble),
            highestPrice = String.format(locale = Locale.getDefault(),"%.2f",highestPrice),
            lowestPrice = String.format(locale = Locale.getDefault(),"%.2f",lowestPrice),
            changePrice = String.format(locale = Locale.getDefault(),"%.2f",highestPrice - lowestPrice),
            monthlyAveragePrice = String.format(locale = Locale.getDefault(),"%.2f",highestPrice*100 / 30),
            transactions = "2465",
            tradeVolume = "4667951",
            tradePrice = "1964618",
            priceEarningsRatio = "1234",
            priceToBookRatio = "123456",
            dividendYield = "11123"

        )
    }
    StockAppTheme {
        HomeScreen(
            state = HomeState(
                items = items,
                searchText = "1234",
                selectedStockUI = items.random()
            ),

        )
    }
}