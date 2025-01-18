package com.lihan.stockapp.feature.home.presentation

import com.lihan.stockapp.feature.core.presentation.UiText

sealed interface HomeUiEvent {
    data class ErrorMessage(
        val errorMessage: UiText
    ): HomeUiEvent
}