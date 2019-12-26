package com.sample.rxnaversearchapi.network.di

import com.sample.rxnaversearchapi.network.api.MovieSearchApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
            .create(MovieSearchApi::class.java)
    }
    single {
        GsonConverterFactory.create()
    }
    single {
        RxJava2CallAdapterFactory.create()
    }
}