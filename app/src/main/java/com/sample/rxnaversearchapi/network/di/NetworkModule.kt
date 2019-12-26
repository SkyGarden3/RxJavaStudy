package com.sample.rxnaversearchapi.network.di

import com.sample.rxnaversearchapi.network.api.MovieSearchApi
import com.sample.rxnaversearchapi.network.api.NaverApiKey
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        get<Retrofit>().create(MovieSearchApi::class.java)
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://openapi.naver.com/")
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .client(get())
            .build()
    }

    single<Converter.Factory> {
        GsonConverterFactory.create()
    }

    single<CallAdapter.Factory> {
        RxJava2CallAdapterFactory.create()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(get { parametersOf(it) })
            }
            .build()
    }

    factory { (chain: Interceptor.Chain) ->
        chain.request().newBuilder()
            .header("X-Naver-Client-Id", NaverApiKey.CLIENT_ID)
            .header("X-Naver-Client-Secret", NaverApiKey.SECRET_KEY)
            .method(chain.request().method(), chain.request().body())
            .build()
    }
}