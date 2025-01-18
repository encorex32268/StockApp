package com.lihan.stockapp.feature.home.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lihan.stockapp.R

@Composable
fun StockSearchBar(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(16.dp),
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    BasicTextField(
        modifier = modifier
            .clip(shape)
            .border(
                width = 1.dp ,
                color = MaterialTheme.colorScheme.onBackground ,
                shape = shape
            )
        ,
        value = text,
        onValueChange = onValueChange ,
        decorationBox = {
            Box(
                modifier = Modifier.padding(12.dp),
                contentAlignment = Alignment.CenterStart
            ){
                if (text.isEmpty()) {
                    Text(
                        text = stringResource(R.string.stock_search_bar_hint),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            fontSize = 16.sp
                        )
                    )
                }
                it()

            }
        },
        textStyle =  MaterialTheme.typography.bodyLarge.copy(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 16.sp
        ),
        cursorBrush = SolidColor(
            MaterialTheme.colorScheme.onBackground
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboard?.hide()
                focusManager.clearFocus()
            }
        )

    )

}