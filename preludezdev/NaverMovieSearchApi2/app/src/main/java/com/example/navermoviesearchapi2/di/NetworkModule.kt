package com.example.navermoviesearchapi2.di

import com.example.navermoviesearchapi2.network.MovieApiService
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val API_URL = "https://openapi.naver.com/"

val networkModule = module {
    single { (get() as Retrofit).create(MovieApiService::class.java) }

    single {
        Retrofit.Builder()
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .baseUrl(API_URL)
            .build()
    }

    single { GsonConverterFactory.create() as Converter.Factory }
    single { RxJava2CallAdapterFactory.create() as CallAdapter.Factory }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(StethoInterceptor())
            .build() as OkHttpClient
    }
}