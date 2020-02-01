package com.architecture.study.data.source.remote.di

import com.architecture.study.data.source.remote.*
import com.architecture.study.network.api.BithumbApi
import com.architecture.study.network.api.CoinoneApi
import com.architecture.study.network.api.UpbitApi
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit

val remoteModule = module {
    single<UpbitApi> { get<Retrofit> { parametersOf("https://api.upbit.com") }.create(UpbitApi::class.java) }
    single<BithumbApi> { get<Retrofit> { parametersOf("https://api.bithumb.com") }.create(BithumbApi::class.java) }
    single<CoinoneApi> { get<Retrofit> { parametersOf("https://api.coinone.co.kr")}.create(CoinoneApi::class.java) }
    single<UpbitRemoteDataSource> { UpbitRemoteDataSourceImpl(get()) }
    single<BithumbRemoteDataSource> { BithumbRemoteDataSourceImpl(get()) }
    single<CoinoneRemoteDataSource> { CoinoneRemoteDataSourceImpl(get()) }
}