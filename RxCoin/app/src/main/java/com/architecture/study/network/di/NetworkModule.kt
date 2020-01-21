package com.architecture.study.network.di

import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory<Retrofit> { (url: String) ->
        Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(get())
            .addConverterFactory(get())
            .build()
    }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    single<Converter.Factory> { GsonConverterFactory.create() }
}