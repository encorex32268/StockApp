package com.lihan.stockapp

import android.app.Application
import com.lihan.stockapp.feature.core.di.appModule
import com.lihan.stockapp.feature.home.di.homeModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class StockApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidLogger()
            androidContext(applicationContext)
            modules(
                appModule,
                homeModule
            )
        }
    }
}