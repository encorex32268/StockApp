package com.lihan.stockapp.feature.home.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.stockapp.R
import com.lihan.stockapp.feature.core.designsystem.ui.theme.DARK_GREEN
import com.lihan.stockapp.feature.core.designsystem.ui.theme.DARK_RED
import com.lihan.stockapp.feature.core.designsystem.ui.theme.LIGHT_GREEN
import com.lihan.stockapp.feature.core.designsystem.ui.theme.LIGHT_RED
import com.lihan.stockapp.feature.core.designsystem.ui.theme.StockAppTheme
import com.lihan.stockapp.feature.home.presentation.model.StockUI
import com.lihan.stockapp.feature.home.presentation.testtags.TestTags

@Composable
fun StockItem(
    modifier: Modifier = Modifier,
    stockUI: StockUI,
    onItemClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        onClick = onItemClick
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = stockUI.code?:"",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = stockUI.name?:"",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.open_price),
                    valueText = stockUI.openingPrice?:"",
                )
                val closingPrice = remember(stockUI) {
                    stockUI.closingPrice?.toDoubleOrNull()?:0.0
                }

                val monthlyAveragePrice = remember(stockUI) {
                    stockUI.monthlyAveragePrice?.toDoubleOrNull()?: 0.0
                }
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.closing_price),
                    valueText = stockUI.closingPrice?:"",
                    valueTextColor = when{
                        closingPrice > monthlyAveragePrice -> if (isSystemInDarkTheme()) DARK_RED else LIGHT_RED
                        closingPrice < monthlyAveragePrice -> if (isSystemInDarkTheme()) DARK_GREEN else LIGHT_GREEN
                        else -> {
                            MaterialTheme.colorScheme.onBackground
                        }
                    }
                )
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.highest_price),
                    valueText = stockUI.highestPrice?:"",
                )
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.lowest_price),
                    valueText = stockUI.lowestPrice?:"",
                )
            }
            val changePrice = remember(stockUI) {
                stockUI.changePrice?.toDoubleOrNull()?:0.0
            }
            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.change_price),
                    valueText = stockUI.changePrice?:"",
                    valueTextColor = when{
                        changePrice < 0 -> if (isSystemInDarkTheme()) DARK_GREEN else LIGHT_GREEN
                        changePrice > 0 -> if (isSystemInDarkTheme()) DARK_RED else LIGHT_RED
                        else -> {
                            MaterialTheme.colorScheme.onBackground
                        }
                    }
                )
                PriceText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.monthly_average_price),
                    valueText = stockUI.monthlyAveragePrice?:"",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                PriceText(
                    title = stringResource(R.string.transactions),
                    valueText = stockUI.transactions?:"",
                    isVertical = true
                )
                PriceText(
                    title = stringResource(R.string.trade_volume),
                    valueText = stockUI.tradeVolume?:"",
                    isVertical = true
                )
                PriceText(
                    title = stringResource(R.string.trade_price),
                    valueText = stockUI.tradePrice?:"",
                    isVertical = true
                )
            }

        }

    }

}

@Composable
fun PriceText(
    modifier: Modifier = Modifier,
    title: String,
    valueText: String,
    valueTextColor: Color = MaterialTheme.colorScheme.onBackground,
    valueFontSize: TextUnit = 16.sp,
    isVertical: Boolean = false
){
    var valueDynamicSize by remember {
        mutableFloatStateOf(valueFontSize.value)
    }
    if (isVertical){
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .align(Alignment.End),
                text = valueText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = valueTextColor,
                    fontSize = valueDynamicSize.sp
                ),
                maxLines = 1,
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        valueDynamicSize -= 1f
                    }
                },
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
        }
    }else{
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
            Text(
                text = valueText,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = valueTextColor,
                    fontSize = valueDynamicSize.sp
                ),
                maxLines = 1,
                onTextLayout = {
                    if (it.hasVisualOverflow) {
                        valueDynamicSize -= 1f
                    }
                },
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End
            )
        }

    }
}


@Composable
@PreviewLightDark
private fun StockItemPreview(
) {
    val stockUiPreview = StockUI(
        code = "1101",
        name = "台泥",
        closingPrice = "99",
        monthlyAveragePrice = "100",
        tradeVolume = "999999999",
        tradePrice = "999999999",
        transactions = "999999999",
        highestPrice = "99999.99",
        lowestPrice = "99999.99",
        changePrice = "99999.99",
        openingPrice = "99999.99"
    )
    StockAppTheme{
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
        ){
            StockItem(
                stockUI = stockUiPreview
            )
        }

    }
}