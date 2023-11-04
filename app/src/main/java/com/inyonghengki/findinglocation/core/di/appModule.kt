package com.inyonghengki.findinglocation.core.di

import com.inyonghengki.findinglocation.core.data.source.local.LocalDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.RemoteDataSource
import com.inyonghengki.findinglocation.core.data.source.remote.network.ApiConfig
import org.koin.dsl.module


val appModule = module {

    single { ApiConfig.provideApiService }

    single { RemoteDataSource(get()) }

    single { LocalDataSource() }
}