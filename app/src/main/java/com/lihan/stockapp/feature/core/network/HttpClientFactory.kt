package com.lihan.stockapp.feature.core.network

import android.util.Log
import com.lihan.stockapp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


class HttpClientFactory {
    fun build(): HttpClient {
        return HttpClient(CIO){
            install(ContentNegotiation){
                json(
                    json = Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(Logging){
                logger = object: Logger {
                    override fun log(message: String) {
                        if (BuildConfig.DEBUG){
                            Log.d("HttpClient", message)
                        }
                    }

                }
                level = LogLevel.ALL
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
            }
        }
    }
}