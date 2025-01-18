package com.lihan.stockapp.feature.home.di

import com.lihan.stockapp.feature.home.data.remote.HomeRepositoryImpl
import com.lihan.stockapp.feature.home.data.remote.StockRemoteDataSourceImpl
import com.lihan.stockapp.feature.home.domain.repository.HomeRepository
import com.lihan.stockapp.feature.home.domain.repository.StockRemoteDataSource
import com.lihan.stockapp.feature.home.presentation.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val homeModule = module {
    singleOf(::StockRemoteDataSourceImpl).bind<StockRemoteDataSource>()
    singleOf(::HomeRepositoryImpl).bind<HomeRepository>()
    viewModel {
        HomeViewModel(
            repository = get()
        )
    }
}