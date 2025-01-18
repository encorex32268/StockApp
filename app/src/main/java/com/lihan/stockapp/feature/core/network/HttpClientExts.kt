package com.lihan.stockapp.feature.core.network

import com.lihan.stockapp.BuildConfig
import com.lihan.stockapp.feature.core.util.DataError
import io.ktor.client.statement.HttpResponse
import com.lihan.stockapp.feature.core.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import kotlin.coroutines.cancellation.CancellationException


suspend inline fun <reified Response: Any> HttpClient.get(
    route: String,
    queryParameters: Map<String,Any?> = mapOf()
): Result<Response, DataError.Network> {
    return safeCall {
        get {
            url(constructRoute(route))
            queryParameters.forEach { (key,value) ->
                parameter(key,value)
            }
        }
    }
}

suspend inline fun <reified T> safeCall(
    execute: () -> HttpResponse
): Result<T, DataError.Network> {
    val response = try {
        execute()
    }catch (e: UnresolvedAddressException){
        e.printStackTrace()
        return Result.Error(DataError.Network.NO_INTERNET)
    }catch (e: SerializationException){
        e.printStackTrace()
        return Result.Error(DataError.Network.SERIALIZATION)
    }catch (e: Exception){
        if (e is CancellationException) throw e
        e.printStackTrace()
        return Result.Error(DataError.Network.UNKNOWN)
    }
    return responseToResult(response)
}

suspend inline fun <reified T> responseToResult(response: HttpResponse): Result<T,DataError.Network>{
    return when(response.status.value){
        in 200..299 -> {
            Result.Success(
                response.body()
            )
        }
        401 -> { Result.Error(DataError.Network.UNAUTHORIZED)}
        408 -> { Result.Error(DataError.Network.REQUEST_TIMEOUT)}
        409 -> { Result.Error(DataError.Network.CONFLICT)}
        413 -> { Result.Error(DataError.Network.PAYLOAD_TOO_LARGE)}
        429 -> { Result.Error(DataError.Network.TOO_MANY_REQUESTS)}
        in 500..599 -> { Result.Error(DataError.Network.SERVER_ERROR)}
        else -> Result.Error(DataError.Network.UNKNOWN)
    }
}

fun constructRoute(route: String): String {
    val baseUrl = BuildConfig.BASE_URL
    return when{
        route.contains(baseUrl) -> route
        route.startsWith("/") -> baseUrl + route
        else -> "${baseUrl}/${route}"
    }
}