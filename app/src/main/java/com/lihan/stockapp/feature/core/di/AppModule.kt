package com.lihan.stockapp.feature.core.di

import com.lihan.stockapp.feature.core.network.HttpClientFactory
import io.ktor.client.HttpClient
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single{
        HttpClientFactory().build()
    }.bind<HttpClient>()
}