package com.lihan.stockapp.feature.core.presentation

import com.lihan.stockapp.R
import com.lihan.stockapp.feature.core.util.DataError

fun DataError.Network.toUiText(): UiText {
    return when(this){
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.error_request_timeout)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.error_unauthorized)
        DataError.Network.CONFLICT -> UiText.StringResource(R.string.error_conflict)
        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(R.string.error_too_many_requests)
        DataError.Network.NO_INTERNET -> UiText.StringResource(R.string.error_no_internet)
        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(R.string.error_payload_too_large)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.error_server_error)
        DataError.Network.SERIALIZATION -> UiText.StringResource(R.string.error_serialization)
        DataError.Network.UNKNOWN -> UiText.StringResource(R.string.error_unknown)
    }
}